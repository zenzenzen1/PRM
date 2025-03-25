package com.example.tourbooking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.Entity.Tour;
import com.example.tourbooking.Entity.User;
import com.example.tourbooking.helpler.FormatUtils;
import com.example.tourbooking.payment.zalopay.app_to_app.Api.CreateOrder;
import com.example.tourbooking.payment.zalopay.app_to_app.Constant.AppInfo;
import com.example.tourbooking.repository.OrderRepository;
import com.example.tourbooking.repository.TourRepository;
import com.example.tourbooking.repository.UserRepository;
import com.example.tourbooking.user.HomeActivity;
import com.example.tourbooking.user.TourDetailActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class TourBooking extends AppCompatActivity {
    private TourRepository tourRepository = null;
    private UserRepository userRepository = null;
    private OrderRepository orderRepository = null;
    Button addCount, subCount, btnSelectDate, btnHome, btnbooKing;
    int mCount=1;
    Order.PaymentMethod defaultPaymentMethod = Order.PaymentMethod.ZALOPAY;
    Order.PaymentMethod paymentMethod = defaultPaymentMethod;
    TextView txtCount, tvTitle, tvLocationFrom, tvLocationTo, tvTourTime, tvDescription,
            tvTourNumber, tvPricePerPerson, tvVoteScore, tvVoteNumber, tvContactNumber, priceTour, tvStartDate, tvEndDate;
    ImageView imgTour;
    double basePrice = 0.0;
    int dateNumber, userId, tourId;
    SharedPreferences preferences;
    Tour tour;
    String imageUriString = "";
    private static final String TAG = "TourBooking";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tour_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tourRepository = new TourRepository(this);
        userRepository = new UserRepository(this);
        orderRepository = new OrderRepository(this);
        // Nhận tour ID từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            tourId =  intent.getIntExtra(TourDetailActivity.KEY_TOUR_ID, 1);
        }

        tour = tourRepository.getTour(tourId);
        if(tour == null){
            Toast.makeText(this, "Tour not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if(!tour.isAvaliable()){
            Toast.makeText(this, "Tour is not available!. Please choose other tour.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userId", 2);
        editor.apply();
        int id = preferences.getInt("userId", 2);
        User user = userRepository.getUserById(id);
        userId = user.getId();


        txtCount = findViewById(R.id.txt_count);
        addCount = findViewById(R.id.btn_addCount);
        subCount = findViewById(R.id.btn_subCount);
        tvTitle = findViewById(R.id.tv_Tittle);
        tvLocationFrom = findViewById(R.id.tv_startLocation);
        tvLocationTo = findViewById(R.id.tv_endLocation);
        tvTourTime = findViewById(R.id.tv_tourTime);
        tvDescription = findViewById(R.id.tv_description);
        tvPricePerPerson = findViewById(R.id.tv_price);
        tvVoteScore = findViewById(R.id.tv_voteScore);
        tvVoteNumber = findViewById(R.id.tv_voteNumber);
//        tvContactNumber = findViewById(R.id.tv_contact);
        imgTour = findViewById(R.id.img_tour);
        priceTour = findViewById(R.id.price_tour);
        tvStartDate = findViewById(R.id.tv_startDate);
        tvEndDate = findViewById(R.id.tv_endDate);
        btnSelectDate = findViewById(R.id.btn_selectDate);
        btnbooKing = findViewById(R.id.btn_confirm);
        btnHome = findViewById(R.id.btn_home);
        RadioGroup paymentMethodRadioGroup = findViewById(R.id.rdGroup_paymentMethod);
        
        
        if (tour != null) {


            Glide.with(this)
                    .load(tour.getImage())
                    .error(R.drawable.placeholder)
                    .into(imgTour);

            basePrice = tour.getPricePerPerson();
            dateNumber = tour.getDateNumber();

            tvTitle.setText(tour.getTile());
            tvLocationFrom.setText(tour.getLocationFrom());
            tvLocationTo.setText(" - " + tour.getLocationTo());
            tvTourTime.setText(tour.getTourTime());

            tvDescription.setText(tour.getDescription());

            tvPricePerPerson.setText(FormatUtils.formatCurrency(tour.getPricePerPerson()));
            tvVoteScore.setText(String.format("%d*", tour.getVoteScore()));
            tvVoteNumber.setText(String.format("(%d)", tour.getVotedNumber()));
//            tvContactNumber.setText(tourList.getContactNumber());
            priceTour.setText(FormatUtils.formatCurrency(basePrice * mCount));


            // Example: Glide.with(this).load(firstTour.getImage()).into(imgTour);
        }

        txtCount.setText(Integer.toString(mCount));

        
        paymentMethodRadioGroup.removeAllViews();
        for (Order.PaymentMethod paymentMethod :
                Order.PaymentMethod.values()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(paymentMethod.getDisplayPaymentMethod());
            radioButton.setTextSize(18);
            radioButton.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // Width
                    ViewGroup.LayoutParams.WRAP_CONTENT  // Height
            );
            int marginInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()
            );
            params.setMargins(0, marginInPx, 0, marginInPx); // marginStart & marginEnd
            radioButton.setLayoutParams(params);
            radioButton.setTag(paymentMethod.ordinal());
            int _id = View.generateViewId();
            radioButton.setId(_id);
            paymentMethodRadioGroup.addView(radioButton);
            if(paymentMethod.ordinal() == defaultPaymentMethod.ordinal()){
                paymentMethodRadioGroup.check(_id);
            }
//            radioButton.setChecked(paymentMethod.ordinal() == defaultPaymentMethod.ordinal());
        }

       

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourBooking.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                txtCount.setText(Integer.toString(mCount));
                priceTour.setText(String.format("%.0f", basePrice * mCount));
            }
        });

        subCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount > 1) {
                    mCount--;
                    txtCount.setText(Integer.toString(mCount));
                    priceTour.setText(String.format("%.0f", basePrice * mCount));
                }
            }
        });
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ZaloPay SDK Init
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        btnbooKing.setOnClickListener(v -> {
            int radioButtonId = paymentMethodRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioButtonId);
            Toast.makeText(this, radioButton.getText() + ", tag = " + radioButton.getTag(), Toast.LENGTH_SHORT).show();
            Order.PaymentMethod paymentMethod = Order.PaymentMethod.values()[(int) radioButton.getTag()];
            Log.d(TAG, "onClick: paymentMethod = " + paymentMethod);
            switch (paymentMethod)
            {
                case ZALOPAY:{
                    handleZaloPay();
                    Log.d(TAG, "handle ZALOPAY");
                    break;
                }
                case COD: {
                    Log.d(TAG, "handle COD");
                    createOrder(Status.StatusEnum.PENDING.getId(), Order.PaymentMethod.COD);
                    break;
                }
            }
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//            createOrder();
        });

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }
    
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TourBooking.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDates(calendar);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDates(Calendar startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        tvStartDate.setText(sdf.format(startDate.getTime()));
        startDate.add(Calendar.DAY_OF_MONTH, dateNumber);
        tvEndDate.setText(sdf.format(startDate.getTime()));
    }

    private void createOrder(int orderStatusId, Order.PaymentMethod paymentMethod) {
        try {
            if(!tour.isAvaliable()){
                Toast.makeText(this, "Tour is not available!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String startDateStr = tvStartDate.getText().toString();
            String endDateStr = tvEndDate.getText().toString();

            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                Toast.makeText(this, "Please select a date!", Toast.LENGTH_SHORT).show();
                return;
            }
            

            Date startDate = sdf.parse(tvStartDate.getText().toString());
            Date endDate = sdf.parse(tvEndDate.getText().toString());
            Date orderDate = new Date();

            Order order = new Order();
            order.setTourId(tour.getId());
            order.setUserId(userId);
            order.setNumberOfPerson(mCount);
            order.setDepartureDay(startDate);
            order.setTotalFee(basePrice * mCount);
            order.setStatusId(orderStatusId);
            order.setOrderDate(orderDate);
            order.setPaymentMethod(paymentMethod.getDisplayPaymentMethod());
            order.setEndDate(endDate);

            orderRepository.createOrder(order);
            tour.setAvaliable(false);
            tourRepository.updateTour(tour);

            Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create order!", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void handleZaloPay(){
        double total = 10_000;
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder("10000");
            String code = data.getString("return_code");
            Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_SHORT).show();
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(this, token, "demozpdk://app", new PayOrderListener(){
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        Log.d("ZaloPay", "onPaymentSucceeded: " + s + "\n" + s1 + "\n" + s2);
                        createOrder(Status.StatusEnum.PENDING.getId(), Order.PaymentMethod.ZALOPAY);
                        Toast.makeText(TourBooking.this, "Checkout successful. Booking Confirmed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Log.d("ZaloPay", "onPaymentCanceled: " + s + "\n" + s1);
//                        Toast.makeText(TourBooking.this, "onPaymentCanceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Log.d("ZaloPay", "onPaymentError: " + zaloPayError + " " + s + "\n" + s1);
                        Toast.makeText(TourBooking.this, "Something error with zalopay. Please try later, or use other method.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}