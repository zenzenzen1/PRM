package com.example.tourbooking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tourbooking.Entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update (Category category);
    @Query("SELECT * FROM Category c WHERE c.id =:categoryId")
    Category select(int categoryId);
    @Query("SELECT * FROM Category WHERE active = 1")
    List<Category> getAllCategories();
    @Query("SELECT * FROM Category")
    List<Category> selectAll();
    @Query("DELETE FROM Category")
    void deleteAll();
    @Query("UPDATE Category SET active = 0 WHERE id = :categoryId")
    void delete(int categoryId);
}
