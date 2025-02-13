package com.example.demofirstapplication.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.demofirstapplication.entity.Word;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRepository extends RoomDatabase {
    
}
