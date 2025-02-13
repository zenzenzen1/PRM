package com.example.demofirstapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class DataStorageDemo extends AppCompatActivity {
    
    private final String FILE_NAME = "myFile.txt";
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_storage_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        
        
        sharedPreferencesDemo();
        
        findViewById(R.id.writeFileButton).setOnClickListener((view) -> {
            writeFile();
        });
        findViewById(R.id.readFileButton).setOnClickListener((view) -> {
            readFile();
        });
    }
    
    private void writeFile(){
        try {
//            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//            outputStreamWriter.write("Hello World");
//            
//            fileOutputStream.close();
//            outputStreamWriter.close();
            String s = "Xin chào. Hê Hê";
            PrintStream output = new PrintStream(openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            output.println(s);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    private void readFile(){
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            StringBuilder data = new StringBuilder();
            int character;
            while ((character = inputStreamReader.read()) != -1){
                data.append((char) character);
            }
            
            fileInputStream.close();
            inputStreamReader.close();
            ((TextView)findViewById(R.id.textView)).setText(data.toString());
            Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        }
        
        
    }
    
    private void sharedPreferencesDemo(){
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);

        var editor = sharedPreferences.edit();
        editor.putString("name", "John Doe");
        editor.putInt("age", 25);
        editor.putBoolean("isStudent", true);
        editor.apply();
        
        sharedPreferences.getAll().forEach((key, value) -> {
            Log.d("DataStorageDemo", key + " : " + value);
        });
        
        sharedPreferences.getAll().forEach((key, value) -> {
            Log.d("DataStorageDemo", "Remove key: " + key);
            editor.remove(key);
            editor.apply();
        });
    }
}












