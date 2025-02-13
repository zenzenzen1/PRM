package com.example.demofirstapplication.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

public class ConstrainLayoutDemo extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.constraint_layout_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ConstrainLayoutDemo.this);
                dialog.setTitle("Exit");
                dialog.setMessage("Do you want to exit?");
                dialog.setIcon(R.drawable.ic_launcher_foreground);
                dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
//                    finish();
                    Toast.makeText(ConstrainLayoutDemo.this, i + "", Toast.LENGTH_SHORT).show();
                });
                dialog.setNegativeButton("No", (dialogInterface, i) -> {
//                    dialogInterface.dismiss();
                    dialogInterface.cancel();
                });
                dialog.create().show();
            }
        });
        
    }
    
    
}
