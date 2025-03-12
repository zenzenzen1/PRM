package com.example.tourbooking.admin.order;// ListOrder.java

import static com.example.tourbooking.R.layout.activity_order_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.R;
import com.example.tourbooking.adapter.AdminOrderAdapter;
import com.example.tourbooking.admin.HomePageAdminActivity;
import com.example.tourbooking.repository.OrderRepository;

import java.util.List;

public class OrderManagement extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminOrderAdapter orderAdapter;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_order_management);

        recyclerView = findViewById(R.id.admin_tour_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderRepository = new OrderRepository(this);
        List<Order> orderList = orderRepository.getAllOrder();

        orderAdapter = new AdminOrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);
        Button back = findViewById(R.id.btn_admin_backtohome);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(OrderManagement.this, HomePageAdminActivity.class);
            startActivity(intent);
        });
    }
}
