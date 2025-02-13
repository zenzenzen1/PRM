package com.example.demofirstapplication.activity.grid_view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int[] imageIds = {R.drawable.screenshot_2024_11_03_202554, R.drawable.screenshot_2024_11_03_202944,
            R.drawable.screenshot_2024_11_03_203153, R.drawable.screenshot_2024_11_03_203343,
            R.drawable.screenshot_2024_11_03_203414, R.drawable.screenshot_2024_11_03_203440,
            R.drawable.screenshot_2024_11_03_203602, R.drawable.screenshot_2024_11_03_203705,
    };
    String[] names = {"Samsung Galaxy S21", "Samsung Galaxy S22", "Samsung Galaxy S23",
            "Samsung Galaxy S24", "Samsung Galaxy S25", "Samsung Galaxy S26",
            "Samsung Galaxy S27", "Samsung Galaxy S28",
    };
    double[] prices = {1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700};
    ArrayList<Item> items;
    ItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GridView gridView = findViewById(R.id.gridView);
        items = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            items.add(new Item(imageIds[i], names[i], prices[i]));
        }
        adapter = new ItemAdapter(this, R.layout.layout_item_grid_view, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Item item = items.get(position);
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        });
    }
}




















