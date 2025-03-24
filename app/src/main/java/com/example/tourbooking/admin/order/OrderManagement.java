package com.example.tourbooking.admin.order;// ListOrder.java

import static com.example.tourbooking.R.layout.activity_order_management;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.R;
import com.example.tourbooking.adapter.AdminOrderAdapter;
import com.example.tourbooking.repository.OrderRepository;
import com.example.tourbooking.repository.StatusRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderManagement extends AppCompatActivity {
    private static final int ALL_STATUS_ID = -1;
    private RecyclerView recyclerView;
    private AdminOrderAdapter orderAdapter;
    private OrderRepository orderRepository;
    List<Order> orderList;
    private StatusRepository statusRepository;
    StatusAdapter statusAdapter;
    Toolbar toolbar;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_order_management);
        orderRepository = new OrderRepository(this);
        statusRepository = new StatusRepository(this);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        spinner = findViewById(R.id.spinner);
        List<Status> items = new ArrayList<>();
        items.add(new Status(-1, "All Status", "All Status"));
        items.addAll(statusRepository.getAllStatus());
        
        // Create Adapter
        statusAdapter = new StatusAdapter(this, items);
        spinner.setAdapter(statusAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status selectedStatus = (Status) parent.getItemAtPosition(position);
                orderList.clear();
                orderList.addAll(selectedStatus.getId() == ALL_STATUS_ID ? orderRepository.getAllOrder() : orderRepository.getOrdersByStatusId(selectedStatus.getId()));
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toolbar.setTitle("Select an Option");
            }
        });
        
        recyclerView = findViewById(R.id.admin_tour_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        
        orderList = orderRepository.getAllOrder();

        orderAdapter = new AdminOrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);
        Button back = findViewById(R.id.btn_admin_backtohome);
        back.setOnClickListener(v -> {
            finish();
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_management_menu, menu);

        // Get SearchView from the menu
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        // Handle search query
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchItems(query); // Implement this method
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchItems(newText); // Implement this method
//                return false;
//            }
//        });

        return true;
    }
    private void showCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");

        String[] categories = {"All", "Pending", "A", "B"};
        builder.setItems(categories, (dialog, which) -> {
            String selectedCategory = categories[which];
            filterByCategory(selectedCategory);
        });

        builder.show();
    }
    private void searchItems(String query) {
        // Implement search logic here
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
    }

    private void filterByCategory(String category) {
        // Implement category filter logic here
        Toast.makeText(this, "Category Selected: " + category, Toast.LENGTH_SHORT).show();
    }
}
