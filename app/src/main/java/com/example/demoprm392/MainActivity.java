package com.example.demoprm392;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demoprm392.demo.ContextMenu;
import com.example.demoprm392.demo.OptionsMenuDemo;

public class MainActivity extends AppCompatActivity {

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
        Button spinnerButton = findViewById(R.id.spinnerButton);
        spinnerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.demoprm392.demo.SpinnerDemo.class);
            startActivity(intent);
        });
        
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, OptionsMenuDemo.class);
            startActivity(intent);
        });
        Button contextMenuButton = findViewById(R.id.contextMenuButton);
        contextMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContextMenu.class);
            startActivity(intent);
        });
        Button contextualMenuButton = findViewById(R.id.contextualMenuButton);
        contextualMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.demoprm392.demo.ContextualMenu.class);
            startActivity(intent);
        });
        Button popUpMenuButton = findViewById(R.id.popupMenuButton);
        popUpMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.demoprm392.demo.PopUpMenu.class);
            startActivity(intent);
        });
        
    }
}