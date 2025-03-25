package com.example.tourbooking.admin.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
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

public class EditCategoryActivity extends AppCompatActivity {

    private EditText edtCategoryName;
    private Switch switchActive;
    private Button btnUpdate, btnBack;
    private ImageButton backHomePageAdmin;
    private CategoryRepository categoryRepository;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo Repository
        categoryRepository = new CategoryRepository(this);

        // Khởi tạo các thành phần giao diện
        edtCategoryName = findViewById(R.id.edt_category_name);
        switchActive = findViewById(R.id.switch_active);
        btnUpdate = findViewById(R.id.btn_update);
        btnBack = findViewById(R.id.btn_back);
        backHomePageAdmin = findViewById(R.id.back_homepage_admin);

        // Lấy categoryId từ Intent
        int categoryId = getIntent().getIntExtra("categoryId", -1);
        if (categoryId != -1) {
            category = categoryRepository.getCategory(categoryId);
            if (category != null) {
                // Hiển thị thông tin hiện tại của danh mục
                edtCategoryName.setText(category.getCategoryName());
                switchActive.setChecked(category.isActive());
            } else {
                Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid category ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Xử lý nút Update
        btnUpdate.setOnClickListener(v -> {
            if (category != null) {
                String newCategoryName = edtCategoryName.getText().toString().trim();
                if (newCategoryName.isEmpty()) {
                    Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật thông tin danh mục
                category.setCategoryName(newCategoryName);
                category.setActive(switchActive.isChecked());
                categoryRepository.updateCategory(category);

                Toast.makeText(this, "Category updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditCategoryActivity.this, ListCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý nút Back (nút văn bản)
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(EditCategoryActivity.this, ListCategoryActivity.class);
            startActivity(intent);
            finish();
        });

        // Xử lý nút quay lại (ImageButton)
        backHomePageAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(EditCategoryActivity.this, HomePageAdminActivity.class);
            startActivity(intent);
            finish();
        });
    }
}