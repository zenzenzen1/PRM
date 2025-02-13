package com.example.demofirstapplication.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.demofirstapplication.entity.Student;
import com.example.demofirstapplication.entity.Word;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentRoomDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();

    public static StudentRoomDatabase INSTANCE = null;

    public static StudentRoomDatabase getDatabase(final Context context) {
        synchronized (StudentRoomDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                StudentRoomDatabase.class, "student_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}


















