package com.example.demofirstapplication.activity.list_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    int[] imageIds = {R.drawable.screenshot_2024_11_03_202554, R.drawable.screenshot_2024_11_03_202944, 
            R.drawable.screenshot_2024_11_03_203153, R.drawable.screenshot_2024_11_03_203343, 
            R.drawable.screenshot_2024_11_03_203414, R.drawable.screenshot_2024_11_03_203440};
    String[] names = {"Samsung Galaxy S21", "Samsung Galaxy S22", "Samsung Galaxy S23", 
            "Samsung Galaxy S24", "Samsung Galaxy S25", "Samsung Galaxy S26"};
    ArrayList<Phone> phones;
    PhoneAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listView);
        phones = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            phones.add(new Phone(imageIds[i], names[i]));
        }
        adapter = new PhoneAdapter(this, R.layout.layout_item, phones);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity2.this, SubActivity.class);
            intent.putExtra("name", phones.get(position).getName());
            intent.putExtra("imageId", phones.get(position).getImageId());
            startActivity(intent);
        });
    }
}














