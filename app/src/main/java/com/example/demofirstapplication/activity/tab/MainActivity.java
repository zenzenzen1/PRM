package com.example.demofirstapplication.activity.tab;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    List<Double> numbers1;
    List<Double> numbers2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText2);
        TabHost tabHost = findViewById(R.id.tabHost);
        Button button = findViewById(R.id.button);
        Button clearButton = findViewById(R.id.clearButton);
        ListView listView = findViewById(R.id.listView);
        FrameLayout tabContent = findViewById(android.R.id.tabcontent);
        items = new ArrayList<>();
        numbers1 = new ArrayList<>();
        numbers2 = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        tabHost.setup();
        TabHost.TabSpec spec1, spec2;
        spec1 = tabHost.newTabSpec("t1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Sum");
        tabHost.addTab(spec1);

        spec2 = tabHost.newTabSpec("t2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("History");
        tabHost.addTab(spec2);
        
        clearButton.setOnClickListener(v -> {
            editText.setText("");
            editText2.setText("");
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                view.clearFocus();
            }
        });
        
        button.setOnClickListener(v -> {
            try {
                double a = Double.parseDouble(editText.getText().toString());
                double b = Double.parseDouble(editText2.getText().toString());
                double c = a + b;
                numbers1.add(a);
                numbers2.add(b);
                items.add(a + " + " + b + " = " + c);
                editText.setText("");
                editText2.setText("");
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    view.clearFocus();
                }
                adapter.notifyDataSetChanged();
            } catch (Exception ignored) {
                editText.setError("Invalid number");
                editText2.setError("Invalid number");

            }
            
        });
        
        tabContent.setOnClickListener(v -> {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                view.clearFocus();
            }
        });


        listView.setOnItemClickListener((parent, view, position, id) -> {
            double a = numbers1.get(position);
            double b = numbers2.get(position);
            editText.setText(String.valueOf(a));
            editText2.setText(String.valueOf(b));
            tabHost.setCurrentTab(0);
        });
    }
}

















