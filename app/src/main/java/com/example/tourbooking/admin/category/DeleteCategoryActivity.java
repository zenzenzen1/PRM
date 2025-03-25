package com.example.tourbooking.admin.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class DeleteCategoryActivity extends AppCompatActivity {

    private TextView tvCategoryName;
    private Button btnConfirmDelete, btnBack;
    private ImageButton backHomePageAdmin;
    private CategoryRepository categoryRepository;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo Repository
        categoryRepository = new CategoryRepository(this);

        // Khởi tạo các thành phần giao diện
        tvCategoryName = findViewById(R.id.tv_category_name);
        btnConfirmDelete = findViewById(R.id.btn_confirm_delete);
        btnBack = findViewById(R.id.btn_back);
        backHomePageAdmin = findViewById(R.id.back_homepage_admin);

        // Lấy categoryId từ Intent
        int categoryId = getIntent().getIntExtra("categoryId", -1);
        if (categoryId != -1) {
            category = categoryRepository.getCategory(categoryId);
            if (category != null) {
                tvCategoryName.setText(category.getCategoryName());
            } else {
                Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid category ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Xử lý nút Confirm Delete
        btnConfirmDelete.setOnClickListener(v -> {
            if (category != null) {
                categoryRepository.delete(category.getId());
                Toast.makeText(this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteCategoryActivity.this, ListCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý nút Back (nút văn bản)
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DeleteCategoryActivity.this, ListCategoryActivity.class);
            startActivity(intent);
            finish();
        });

        // Xử lý nút quay lại (ImageButton)
        backHomePageAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(DeleteCategoryActivity.this, HomePageAdminActivity.class);
            startActivity(intent);
            finish();
        });
    }
}