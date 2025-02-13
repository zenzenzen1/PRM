package com.example.demofirstapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

public class UserInformationActivity extends AppCompatActivity {
    
    private EditText name, age, extraInfo;
    private Button submitButton;
    private RadioGroup levelRadioGroup;
    private CheckBox readingCheckBox, gamingCheckBox, codingCheckBox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        extraInfo = findViewById(R.id.extraInfo);
        submitButton = findViewById(R.id.submitButton);
        levelRadioGroup = findViewById(R.id.radioGroupLevel);
        readingCheckBox = findViewById(R.id.checkBoxReading);
        gamingCheckBox = findViewById(R.id.checkBoxGaming);
        codingCheckBox = findViewById(R.id.checkBoxCoding);
        
        submitButton.setOnClickListener(v -> {
            String nameText = name.getText().toString();
            if(nameText.length() < 3){
                name.setError("Name must be at least 3 characters long");
                name.requestFocus();
                return;
            }
            String ageText = age.getText().toString();
            String extraInfoText = extraInfo.getText().toString();
            
            int groupCheckedId = levelRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(groupCheckedId);
            String levelText = radioButton.getText().toString();
            
            String hobbies = "";
            if(readingCheckBox.isChecked()){
                hobbies += "Reading, ";
            }
            if(gamingCheckBox.isChecked()){
                hobbies += "Gaming, ";
            }
            if(codingCheckBox.isChecked()){
                hobbies += "Coding, ";
            }
            String info;
            Log.d("UserInformationActivity", info = "Name: " + nameText + ", Age: " + ageText + ", Level: " + levelText + ", Hobbies: " + hobbies + ", Extra Info: " + extraInfoText);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("User Information");
            dialog.setMessage(info);
            dialog.setPositiveButton("OK", (dialogInterface, i) -> {
                dialogInterface.cancel();
                Log.d("UserInformationActivity", "info dialog closed" + " " + i);
            });
            dialog.create().show();
            
            
        });
        
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserInformationActivity.this);
                dialog.setTitle("Exit");
                dialog.setMessage("Are you sure you want to exit?");
                dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                    finish();
                });
                dialog.setNegativeButton("No", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });
                dialog.create().show();
            }
        });
    }
}













