package com.example.demoprm392.demo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.demoprm392.R;

public class PopUpMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pop_up_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.showPopUpMenu);

        PopupMenu popupMenu = new PopupMenu(this, button);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.option_1) {
                textView.setText("Option 1 selected");
            } else if (id == R.id.option_2) {
                textView.setText("Option 2 selected");
            } else if (id == R.id.option_3) {
                textView.setText("Option 3 selected");
            }
            return true;
        });
        
        button.setOnClickListener(v -> {
            popupMenu.show();
        });
        
    }
}