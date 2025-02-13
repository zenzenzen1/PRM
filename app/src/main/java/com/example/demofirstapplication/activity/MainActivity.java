package com.example.demofirstapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;
import com.example.demofirstapplication.permission.Permission;
import com.example.demofirstapplication.service.MusicService;

public class MainActivity extends AppCompatActivity {
    TextView textViewResult;
    boolean isMusicPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
//            return;
//        }
        if(!Permission.checkPermission(this, this, Manifest.permission.RECEIVE_SMS)){
        }
        if(!Permission.checkPermission(this, this, Manifest.permission.READ_SMS)){
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent dataIntent = result.getData();
                        String resultData = dataIntent.getStringExtra("data");
                        textViewResult.setText(resultData);
                    }
                    Toast.makeText(this, "ActivityResultLauncher", Toast.LENGTH_SHORT).show();
                });
        Button openSecondActivityButton = findViewById(R.id.button);
        Button addButton = findViewById(R.id.buttonAdd);
        Button requestButton = findViewById(R.id.buttonRequest);
        Button callButton = findViewById(R.id.buttonCall);
        Button smsButton = findViewById(R.id.buttonSMS);
        Button linkButton = findViewById(R.id.buttonLink);
        ImageButton musicButton = findViewById(R.id.buttonMusic);
        ImageButton stopMusicButton = findViewById(R.id.buttonStop);
        
        EditText edtLink = findViewById(R.id.editTextLink);
        edtLink.setText("www.google.com");
        EditText edtA = findViewById(R.id.editTextA);
        EditText edtB = findViewById(R.id.editTextB);
        EditText edtRequest = findViewById(R.id.editTextData);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        textViewResult = findViewById(R.id.textViewResult);

        openSecondActivityButton.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, SecondActivity.class);
            startActivity(secondActivityIntent);
        });

        addButton.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, SecondActivity.class);
            try {
                int a = Integer.parseInt(edtA.getText().toString());
                int b = Integer.parseInt(edtB.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putInt("a", a);
                bundle.putInt("b", b);
//                secondActivityIntent.putExtra("data", bundle);
                secondActivityIntent.putExtra("a", a);
                secondActivityIntent.putExtra("b", b);
                startActivity(secondActivityIntent);
            } catch (NumberFormatException e) {
                edtA.setError("Please enter a number");
                edtB.setError("Please enter a number");
            }

        });

        requestButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            String data = edtRequest.getText().toString();
            intent.putExtra("data", data);
//            startActivityForResult(intent, 1);
            activityResultLauncher.launch(intent);
        });
        
        callButton.setOnClickListener(v -> {
            String phone = editTextPhone.getText().toString();
            if(phone.isEmpty()){
                editTextPhone.setError("Please enter a phone number");
                return;
            }
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(callIntent);
        });
        smsButton.setOnClickListener(v -> {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + editTextPhone.getText().toString()));
            startActivity(smsIntent);
        });
        
        linkButton.setOnClickListener(v -> {
            String link = edtLink.getText().toString();
            if(link.isEmpty()){
                edtLink.setError("Please enter a link");
                return;
            }
            Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + link));
            startActivity(linkIntent);
        });
        
        musicButton.setOnClickListener(v -> {
            Intent musicIntent = new Intent(this, MusicService.class);
            startService(musicIntent);
            if(isMusicPlaying){
                musicButton.setImageResource(R.drawable.pause);
                isMusicPlaying = false;
            } else {
                musicButton.setImageResource(R.drawable.play);
                isMusicPlaying = true;
            }
        });
        stopMusicButton.setOnClickListener(v -> {
            Intent musicIntent = new Intent(this, MusicService.class);
            stopService(musicIntent);
            musicButton.setImageResource(R.drawable.play);
            isMusicPlaying = true; 
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, data == null ? "No data" : data.getStringExtra("data"), Toast.LENGTH_SHORT).show();
            textViewResult.setText(data.getStringExtra("data"));
        }
        Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();
    }
}























