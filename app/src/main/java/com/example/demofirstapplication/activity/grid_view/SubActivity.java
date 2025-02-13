package com.example.demofirstapplication.activity.grid_view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item");
        if(item == null) {
            Toast.makeText(this, "Item is null", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Toast.makeText(this, "Item: " + item, Toast.LENGTH_SHORT).show();
        TextView nameTextView = findViewById(R.id.textView);
        TextView priceTextView = findViewById(R.id.textView2);
        ImageView imageView = findViewById(R.id.imageView);
        nameTextView.setText(item.getName());
        priceTextView.setText("Price: " + item.getPrice());
        imageView.setImageResource(item.getImageId()); 
    }
}