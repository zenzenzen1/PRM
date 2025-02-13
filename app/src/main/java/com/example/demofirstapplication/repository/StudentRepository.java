package com.example.demofirstapplication.repository;

import android.content.Context;

import com.example.demofirstapplication.dao.StudentDao;
import com.example.demofirstapplication.dao.StudentRoomDatabase;
import com.example.demofirstapplication.entity.Student;

public class StudentRepository {
    private StudentDao studentDao;
    private Context context;
    
    public StudentRepository(Context context) {
        this.context = context;
        studentDao = StudentRoomDatabase.getDatabase(context).studentDao();
    }
    
    public void addStudent(Student student) {
        studentDao.insert(student);
    }
}
