package com.example.tourbooking.OrderTour;// ListOrder.java

import static com.example.tourbooking.R.layout.activity_tour_histories;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.R;
import com.example.tourbooking.adapter.OrderAdapter;
import com.example.tourbooking.repository.OrderRepository;

import java.util.List;

public class ListOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_tour_histories);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRepository = new OrderRepository(getApplication());
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);


        List<Order> orderList = orderRepository.getOrdersByUserId(userId);

        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

        Button back = findViewById(R.id.btn_backtohome);
        back.setOnClickListener(v -> {
            finish();
        });
    }
}
