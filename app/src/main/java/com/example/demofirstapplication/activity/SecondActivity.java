package com.example.demofirstapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

public class SecondActivity extends AppCompatActivity {
    Button openMainActivityButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        openMainActivityButton = findViewById(R.id.button);
        TextView textViewResult = findViewById(R.id.textViewResult);
        ImageButton captureImageButton = findViewById(R.id.imageButtonCapture);
        ActivityResultLauncher<Intent> captureActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if(result.getData() == null || result.getData().getExtras() == null){
                            return;
                        }
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        ImageView imageView = new ImageView(this);
                        imageView.setImageBitmap(photo);
                        imageView.setId(View.generateViewId());
                        LinearLayout layout = findViewById(R.id.main);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        layout.addView(imageView);
                    }
                });
        openMainActivityButton.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        if(intent != null){
            int a = intent.getIntExtra("a", 0);
            int b = intent.getIntExtra("b", 0);
            int result = a + b;
            String resultString = "Result: " + result;
            textViewResult.setText(resultString);
        }else{
            Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show();
        }
        
        captureImageButton.setOnClickListener(v -> {
            Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return;
            }
            captureActivityResultLauncher.launch(captureImageIntent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}