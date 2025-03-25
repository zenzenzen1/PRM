package com.example.tourbooking.OrderTour;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.Entity.Vote;
import com.example.tourbooking.R;
import com.example.tourbooking.repository.OrderRepository;
import com.example.tourbooking.repository.TourRepository;
import com.example.tourbooking.repository.VoteRepository;

public class OrderDetailActivity extends AppCompatActivity {

    private OrderRepository orderRepository;
    private VoteRepository voteRepository;
    private Order order;
    private TourRepository tourRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        voteRepository = new VoteRepository(this);
        tourRepository = new TourRepository(this);
        orderRepository = new OrderRepository(this);
        EditText edtVote = findViewById(R.id.edt_vote);
        Button btnVote = findViewById(R.id.btn_vote);


        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", -1);
        int tourIdd = intent.getIntExtra("tourId", -1);
        String tourName = intent.getStringExtra("tourName");
        String userName = intent.getStringExtra("userName");
        String image = intent.getStringExtra("image");
        // Xử lý lấy thông tin chi tiết của order từ orderId
        order = orderRepository.getOrder(orderId);

        Vote vote = voteRepository.getVoteByUserIdTourId(order.getUserId(), order.getTourId());
        if(vote != null){
            edtVote.setText(vote.getVotedNumber() + "");
        }

        TextView tourNameTextView = findViewById(R.id.textview_tourname_detail);
        tourNameTextView.setText(tourName);
        tourNameTextView.setMaxWidth(500);
        tourNameTextView.setEllipsize(TextUtils.TruncateAt.END);
        tourNameTextView.setSingleLine(false);
        
        
        TextView fee = findViewById(R.id.textview_TotalFee);
        fee.setText("Total fee: " + String.valueOf(order.getTotalFee()));
        TextView status = findViewById(R.id.textview_Status);
        status.setText(Status.StatusEnum.getStatusNameById(order.getStatusId()));
        TextView userNamee = findViewById(R.id.textView_UserName);
        userNamee.setText(userName);
        TextView numPer = findViewById(R.id.textView_NumPer);
        numPer.setText(String.valueOf(order.getNumberOfPerson()));
        TextView orderDay = findViewById(R.id.textView_OrderDay);
        orderDay.setText(String.valueOf(order.getOrderDate()));
        TextView departDayy = findViewById(R.id.textView_DeparDay);
        departDayy.setText(String.valueOf(order.getDepartureDay()));
        ImageView imageView = findViewById(R.id.img_tour_detail);
        Glide.with(this)
                .load(image)
                .error(R.drawable.placeholder)
                .into(imageView);
        Button back = findViewById(R.id.btn_backtolistorder);
        back.setOnClickListener(v -> {
            finish();
//            Intent intent1 = new Intent(OrderDetailActivity.this, ListOrder.class);
//            startActivity(intent1);
        });
        btnVote.setOnClickListener(v -> {
            String voteInput = edtVote.getText().toString().trim();
            if (!voteInput.isEmpty()) {
                int voteValue = Integer.parseInt(voteInput);
                if (voteValue >= 0 && voteValue <= 5) { // Assuming vote range
                    // Update Tour voteNumber and voteScore
                    boolean updateSuccess = tourRepository.updateTourVote(order.getUserId(), tourIdd, voteValue);
                    if (updateSuccess) {
                        
                        Toast.makeText(OrderDetailActivity.this, "Vote updated successfully", Toast.LENGTH_SHORT).show();
                        // Optionally update UI or navigate back
                    } else {
                        Toast.makeText(OrderDetailActivity.this, "Failed to update vote", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OrderDetailActivity.this, "Vote value must be between 0 and 5", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OrderDetailActivity.this, "Please enter a vote value", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
