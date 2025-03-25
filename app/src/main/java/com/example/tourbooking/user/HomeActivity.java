//package com.example.carbooking.user;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//
//import com.bumptech.glide.Glide;
//import com.example.carbooking.EditUser;
//import com.example.carbooking.Entity.User;
//import com.example.carbooking.LoginPage;
//import com.example.carbooking.OrderTour.ListOrder;
//import com.example.carbooking.R;
//import com.example.carbooking.databinding.ActivityHomeBinding;
//import com.example.carbooking.databinding.HeaderHomeNavigationBinding;
//import com.example.carbooking.repository.TourRepository;
//import com.example.carbooking.repository.UserRepository;
//import com.example.carbooking.user.adapter.TourAdapter;
//import com.example.carbooking.user.decorator.GridSpacingItemDecoration;
//import com.google.android.material.navigation.NavigationView;
//
//public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    private ActivityHomeBinding binding;
//
//    private SharedPreferences preferences;
//    private UserRepository userRepository;
//    private TourRepository tourRepository;
//
//    private TourAdapter tourAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityHomeBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        initRepositories();
//        initialize();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        tourAdapter.submitList(tourRepository.getAllTour());
//    }
//
//    private void initRepositories() {
//        userRepository = new UserRepository(this);
//        tourRepository = new TourRepository(this);
//        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//    }
//
//    private void initialize() {
//        // Init Toolbar
//        setSupportActionBar(binding.toolbar);
//
//        // Init RecyclerView
//        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 32, true));
//        binding.recyclerView.setAdapter(tourAdapter = new TourAdapter());
//
//        // Init DrawerLayout
//        binding.navView.setNavigationItemSelectedListener(this);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
//                R.string.app_name, R.string.app_name);
//        binding.drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        // Set user info
//        HeaderHomeNavigationBinding headerBinding = HeaderHomeNavigationBinding
//                .bind(binding.navView.getHeaderView(0));
//        int userId = preferences.getInt("userId", -1);
//        User user = userRepository.getUserById(userId);
//        if (user != null) {
//            Glide.with(this)
//                    .load(user.getAvatar())
//                    .error(R.drawable.ic_user)
//                    .into(headerBinding.imageAvatar);
//            headerBinding.textUsername.setText(user.getUserName());
//            headerBinding.textEmail.setText(user.getEmail());
//        }
//
//        // Set listeners
//        headerBinding.viewInfo.setOnClickListener(v -> {
//            Intent intent = new Intent(HomeActivity.this, EditUser.class);
//            startActivity(intent);
//            binding.drawerLayout.closeDrawer(GravityCompat.START);
//        });
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        // Handle log out
//        if (menuItem.getItemId() == R.id.action_logout) {
//            preferences.edit().clear().apply();
//            startActivity(new Intent(this, LoginPage.class));
//            finish();
//        } else if (menuItem.getItemId() == R.id.action_history) {
//            Intent intent = new Intent(this, ListOrder.class);
//            startActivity(intent);
//        }
//
//        binding.drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//}
package com.example.tourbooking.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.example.tourbooking.EditUser;
import com.example.tourbooking.Entity.Category;
import com.example.tourbooking.Entity.Tour;
import com.example.tourbooking.Entity.User;
import com.example.tourbooking.LoginPage;
import com.example.tourbooking.OrderTour.ListOrder;
import com.example.tourbooking.R;
import com.example.tourbooking.databinding.ActivityHomeBinding;
import com.example.tourbooking.databinding.HeaderHomeNavigationBinding;
import com.example.tourbooking.repository.CategoryRepository;
import com.example.tourbooking.repository.TourRepository;
import com.example.tourbooking.repository.UserRepository;
import com.example.tourbooking.user.adapter.TourAdapter;
import com.example.tourbooking.user.decorator.GridSpacingItemDecoration;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomeBinding binding;
    private SharedPreferences preferences;
    private UserRepository userRepository;
    private TourRepository tourRepository;
    private CategoryRepository categoryRepository;
    private TourAdapter tourAdapter;
    private List<Category> activeCategories;
    private List<Tour> fullTourList; // Lưu danh sách đầy đủ để lọc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRepositories();
        initialize();
        setupTabLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Làm mới danh sách tour khi quay lại
        updateTourList();
    }

    private void initRepositories() {
        userRepository = new UserRepository(this);
        tourRepository = new TourRepository(this);
        categoryRepository = new CategoryRepository(this);
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    private void initialize() {
        // Init Toolbar
        setSupportActionBar(binding.toolbar); // Thiết lập Toolbar làm ActionBar

        // Init RecyclerView
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 32, true));
        binding.recyclerView.setAdapter(tourAdapter = new TourAdapter());

        // Init DrawerLayout
        binding.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // Thêm biểu tượng menu (ba gạch ngang)

        // Set user info
        HeaderHomeNavigationBinding headerBinding = HeaderHomeNavigationBinding
                .bind(binding.navView.getHeaderView(0));
        int userId = preferences.getInt("userId", -1);
        User user = userRepository.getUserById(userId);
        if (user != null) {
            Glide.with(this)
                    .load(user.getAvatar())
                    .error(R.drawable.ic_user)
                    .into(headerBinding.imageAvatar);
            headerBinding.textUsername.setText(user.getUserName());
            headerBinding.textEmail.setText(user.getEmail());
        }

        // Set listeners
        headerBinding.viewInfo.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EditUser.class);
            startActivity(intent);
            binding.drawerLayout.closeDrawer(GravityCompat.START);

        });

    }

    private void setupTabLayout() {
        // Lấy danh sách các danh mục đã active
        activeCategories = categoryRepository.getAllCategorys(); // Chỉ lấy các danh mục active = true

        // Thêm tab "All"
        binding.tabs.addTab(binding.tabs.newTab().setText("All"));

        // Thêm các danh mục đã active vào TabLayout
        for (Category category : activeCategories) {
            binding.tabs.addTab(binding.tabs.newTab().setText(category.getCategoryName()));
        }

        // Xử lý sự kiện khi chọn tab
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTourList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                updateTourList();
            }
        });

        // Hiển thị tất cả tour khi khởi động
        updateTourList();
    }

    private void updateTourList() {
        // Lấy tab được chọn
        TabLayout.Tab selectedTab = binding.tabs.getTabAt(binding.tabs.getSelectedTabPosition());
        if (selectedTab == null) return;

        List<Tour> tourList;
        if (selectedTab.getPosition() == 0) {
            // Tab "All" được chọn, hiển thị tất cả tour
            tourList = tourRepository.getAllTour();
        } else {
            // Lấy danh mục tương ứng với tab được chọn
            Category selectedCategory = activeCategories.get(selectedTab.getPosition() - 1); // -1 vì tab đầu tiên là "All"
            tourList = tourRepository.getToursByCategoryId(selectedCategory.getId());
        }

        // Cập nhật danh sách tour trong RecyclerView
        fullTourList = new ArrayList<>(tourList);
        tourAdapter.submitList(tourList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        // Thiết lập SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search tours...");

        // Xử lý sự kiện khi người dùng nhập từ khóa tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTours(newText);
                return true;
            }
        });

        // Xử lý khi SearchView đóng (hiển thị lại danh sách ban đầu)
        searchView.setOnCloseListener(() -> {
            updateTourList();
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void filterTours(String query) {
        if (fullTourList == null) return;

        List<Tour> filteredList = new ArrayList<>();
        String queryLowerCase = query.toLowerCase();

        for (Tour tour : fullTourList) {
            String combinedData = (tour.getTile() + " " +
                    tour.getLocationTo() + " " +
                    tour.getLocationFrom() + " " +
                    String.valueOf(tour.getPricePerPerson())).toLowerCase();

            // Tìm kiếm từ khóa trong chuỗi tổng hợp
            if (combinedData.contains(queryLowerCase)) {
                filteredList.add(tour);
            }
        }

        // Hiển thị kết quả tìm kiếm trên homepage
        tourAdapter.submitList(filteredList);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle log out
        if (menuItem.getItemId() == R.id.action_logout) {
            preferences.edit().clear().apply();
            startActivity(new Intent(this, LoginPage.class));
            finish();
        } else if (menuItem.getItemId() == R.id.action_history) {
            Intent intent = new Intent(this, ListOrder.class);
            startActivity(intent);
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}