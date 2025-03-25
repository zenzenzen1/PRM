package com.example.tourbooking.admin.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tourbooking.Entity.Category;
import com.example.tourbooking.R;
import com.example.tourbooking.admin.HomePageAdminActivity;
import com.example.tourbooking.repository.CategoryRepository;

public class AddCategoryActivity extends AppCompatActivity {
    private EditText edtCategoryName;
    private Button btnSave, btnBack;
    private ImageButton backHomePageAdmin;
    private CategoryRepository categoryRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo Repository
        categoryRepository = new CategoryRepository(this);

        // Khởi tạo các thành phần giao diện
        edtCategoryName = findViewById(R.id.edt_category_name);
        btnSave = findViewById(R.id.btn_addCategory);
        btnBack = findViewById(R.id.btn_back);
        backHomePageAdmin = findViewById(R.id.back_homepage_admin);

        // Xử lý nút Save
        btnSave.setOnClickListener(v -> {
            String categoryName = edtCategoryName.getText().toString().trim();
            if (categoryName.isEmpty()) {
                edtCategoryName.setError("Category Name is required");
            } else {
                Category category = new Category(categoryName);
                categoryRepository.createCategory(category); // Sử dụng insertCategory thay vì createCategory
                Toast.makeText(AddCategoryActivity.this, "Add category successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddCategoryActivity.this, ListCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý nút Back (nút văn bản)
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddCategoryActivity.this, ListCategoryActivity.class);
            startActivity(intent);
            finish();
        });

        // Xử lý nút quay lại (ImageButton)
        backHomePageAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(AddCategoryActivity.this, HomePageAdminActivity.class);
            startActivity(intent);
            finish();
        });
    }
}