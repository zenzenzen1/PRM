package com.example.demofirstapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText textViewReceivedData = findViewById(R.id.textViewReceivedData);
        Button sendOriginalDataButton = findViewById(R.id.buttonSendOriginalData);
        Button sendModifiedDataButton = findViewById(R.id.buttonSendModifiedData);
        Intent intent = getIntent();
        
        String data = intent.getStringExtra("data");
        if(data == null){
            Toast.makeText(this, "No Data Received", Toast.LENGTH_SHORT).show();
        }
        textViewReceivedData.setText(data);
        sendOriginalDataButton.setOnClickListener(v -> {
            intent.putExtra("data", intent.getStringExtra("data"));
            setResult(RESULT_OK, intent);
            finish();
        });
        sendModifiedDataButton.setOnClickListener(v -> {
            intent.putExtra("data", textViewReceivedData.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}