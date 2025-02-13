package com.example.demofirstapplication.activity.storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

public class SharedPrefers extends AppCompatActivity {
    String history = "";
    private static final String SHARED_PREFERS_FILE = "history";
    private static final String HISTORY_KEY = "history";
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HISTORY_KEY, history.trim());
        editor.apply();
        Toast.makeText(this, "onPause_ShardPrefers", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shared_prefers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText editTextA = findViewById(R.id.editTextA);
        EditText editTextB = findViewById(R.id.editTextB);
        Button addButton = findViewById(R.id.buttonAdd);
        Button clearButton = findViewById(R.id.buttonClear);
        TextView textViewResult = findViewById(R.id.textViewHistory);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERS_FILE, MODE_PRIVATE);
        history = sharedPreferences.getString(HISTORY_KEY, "");
//        history = history.isEmpty() ? "" : (history.trim() + "\n");
        textViewResult.setText(history);
        addButton.setOnClickListener(v -> {
            double a = Double.parseDouble(editTextA.getText().toString());
            double b = Double.parseDouble(editTextB.getText().toString());
            double result = a + b;
            history += (history.trim().isEmpty() ? "" : "\n") + a + " + " + b + " = " + result;
            textViewResult.setText(history);
        });
        
        clearButton.setOnClickListener(v -> {
            history = "";
            textViewResult.setText(history);
        });
    }
}














