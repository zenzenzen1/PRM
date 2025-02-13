package com.example.demofirstapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_view_activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        loginButton.setOnClickListener((view) -> {
            Toast.makeText(this, "Login button clicked", Toast.LENGTH_SHORT).show();
            
        });
        
        
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton){
            Toast.makeText(this, "Login button clicked", Toast.LENGTH_SHORT).show();
        }
    }
}























