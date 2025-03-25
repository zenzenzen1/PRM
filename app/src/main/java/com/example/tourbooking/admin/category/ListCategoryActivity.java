package com.example.tourbooking.admin.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourbooking.Entity.Category;
import com.example.tourbooking.R;
import com.example.tourbooking.adapter.CategoryAdapter;
import com.example.tourbooking.admin.HomePageAdminActivity;
import com.example.tourbooking.repository.CategoryRepository;

import java.util.List;

public class ListCategoryActivity extends AppCompatActivity {
    private CategoryRepository categoryRepository = null;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_category);

        // Khởi tạo Repository
        categoryRepository = new CategoryRepository(this);

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = findViewById(R.id.category_list_recycle_view);
        List<Category> categoryList = categoryRepository.getAllCategory();
        CategoryAdapter.OnItemClickListener onItemClickListener = new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Category clickedCategory = categoryList.get(position);
                Intent intent = new Intent(ListCategoryActivity.this, EditCategoryActivity.class);
                intent.putExtra("categoryId", clickedCategory.getId());
                startActivity(intent);
                Toast.makeText(ListCategoryActivity.this, "Edit " + clickedCategory.getCategoryName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                Category category = categoryList.get(position);
                Intent intent = new Intent(ListCategoryActivity.this, DeleteCategoryActivity.class);
                intent.putExtra("categoryId", category.getId());
                startActivity(intent);
            }
        };

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryList, onItemClickListener);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Xử lý nút Add
        Button btnAddCategory = findViewById(R.id.btn_add_category);
        btnAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(ListCategoryActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        });

        // Xử lý nút Back
        back = findViewById(R.id.admin_list_category_back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(ListCategoryActivity.this, HomePageAdminActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Làm mới danh sách khi quay lại
        RecyclerView recyclerView = findViewById(R.id.category_list_recycle_view);
        List<Category> updatedList = categoryRepository.getAllCategory();
        ((CategoryAdapter) recyclerView.getAdapter()).setCategoryList(updatedList);
    }
}