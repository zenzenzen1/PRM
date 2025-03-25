package com.example.tourbooking.admin.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.Entity.Tour;
import com.example.tourbooking.Entity.User;
import com.example.tourbooking.R;
import com.example.tourbooking.helpler.FormatUtils;
import com.example.tourbooking.repository.OrderRepository;
import com.example.tourbooking.repository.StatusRepository;
import com.example.tourbooking.repository.TourRepository;
import com.example.tourbooking.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetail extends AppCompatActivity {

    private OrderRepository orderRepository;
    private Order order;
    private TourRepository tourRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    Button rejectButton, acceptButton, saveButton;
    Spinner spinner;
    ChangeStatusAdapter statusAdapter;
    private int tourId;
    boolean isUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_detail);
        tourRepository = new TourRepository(this);
        userRepository = new UserRepository(this);
        statusRepository = new StatusRepository(this);
        orderRepository = new OrderRepository(this);
       
        spinner = findViewById(R.id.spinner_status);
        rejectButton = findViewById(R.id.btn_Reject);
        acceptButton = findViewById(R.id.btn_Accept);
        saveButton = findViewById(R.id.btn_save);
        
        TextView status = findViewById(R.id.admin_detail_Status);
        
        saveButton.setOnClickListener(v -> {
            if(order.getStatusId() == Status.StatusEnum.COMPLETED.getId()){
                if(order.getEndDate().compareTo(new Date()) > 0) {
                    Toast.makeText(this, "Completed only if end date <= current date", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(order.getStatusId() == Status.StatusEnum.COMPLETED.getId() || order.getStatusId() == Status.StatusEnum.REJECTED.getId() || order.getStatusId() == Status.StatusEnum.CANCELLED.getId() ){
                Tour tour = tourRepository.getTour(order.getTourId());
                tour.setAvaliable(true);
                tourRepository.updateTour(tour);
            }
            orderRepository.updateOrder(order);
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
            if(order.getStatusId() == Status.StatusEnum.COMPLETED.getId() || order.getStatusId() == Status.StatusEnum.REJECTED.getId() || order.getStatusId() == Status.StatusEnum.CANCELLED.getId()){
                spinner.setVisibility(View.GONE);
                status.setText(String.format("Status: %s", Status.StatusEnum.getStatusNameById(order.getStatusId())));
            }
            isUpdated = true;
        });
        
        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", -1);
        int tourId = intent.getIntExtra("tourId", -1);
        order = orderRepository.getOrder(orderId);
        
        if(order.getStatusId() == Status.StatusEnum.COMPLETED.getId() || order.getStatusId() == Status.StatusEnum.REJECTED.getId() || order.getStatusId() == Status.StatusEnum.CANCELLED.getId()){
            spinner.setVisibility(View.GONE);
            status.setText(String.format("Status: %s", Status.StatusEnum.getStatusNameById(order.getStatusId())));
        }
        else{
            List<Status> items = new ArrayList<>(statusRepository.getAllStatus());
            statusAdapter = new ChangeStatusAdapter(this, items);
            spinner.setAdapter(statusAdapter);
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == order.getStatusId()) {
                    spinner.setSelection(i);
                    break;
                }
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Status selectedStatus = (Status) parent.getItemAtPosition(position);
                    order.setStatusId(selectedStatus.getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        
        
        
        
        double fee = intent.getDoubleExtra("fee", 1);
        int statusId = intent.getIntExtra("statusId", 1);
        int userId = intent.getIntExtra("userId", -1);
        int numPer = intent.getIntExtra("numPer", 1);
        String orderDay = intent.getStringExtra("orderDay");
        String departDay = intent.getStringExtra("departDay");
        String endDay = intent.getStringExtra("endDay");
        Tour tour = tourRepository.getTour(tourId);
        // Xử lý lấy thông tin chi tiết của order từ orderId
        
        
        User user = userRepository.getUserById(userId);
        
        TextView phone = findViewById(R.id.admin_detail_Phone);
        TextView email = findViewById(R.id.admin_detail_Email);
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        TextView tourName = findViewById(R.id.admin_name_detail);
        tourName.setText(tour.getTile());
        tourName.setMaxWidth(500);
        tourName.setEllipsize(TextUtils.TruncateAt.END);
        tourName.setSingleLine(false);
        TextView tv_fee = findViewById(R.id.admin_detail_TotalFee);
        tv_fee.setText(String.format("Total fee: %s", FormatUtils.formatCurrency(order.getTotalFee())));
        
        TextView userName = findViewById(R.id.admin_detail_UserName);
        userName.setText(user.getUserName());
        TextView numPerr = findViewById(R.id.admin_detail_NumPer);
        numPerr.setText(String.valueOf(numPer));
        TextView orderDayy = findViewById(R.id.admin_detail_OrderDay);
        orderDayy.setText(FormatUtils.formatDate(order.getOrderDate()));
        TextView departDayy = findViewById(R.id.admin_detail_DepartureDay);
        TextView tv_endDay = findViewById(R.id.admin_detail_EndDay);
        tv_endDay.setText(FormatUtils.formatDate(order.getEndDate()));
        departDayy.setText(FormatUtils.formatDate(order.getDepartureDay()));



        ImageView imageView = findViewById(R.id.admin_img_tour_detail);
        Glide.with(this)
                .load(tour.getImage())
                .error(R.drawable.placeholder)
                .into(imageView);
        Button back = findViewById(R.id.btn_backtolistorder);
        back.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}
