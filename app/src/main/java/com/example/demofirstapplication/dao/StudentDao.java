package com.example.demofirstapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.demofirstapplication.entity.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Student student);
    
    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Student student);
    
    @Query("select * from student_table")
    List<Student> getAllStudents();
}
