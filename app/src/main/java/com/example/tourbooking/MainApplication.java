package com.example.tourbooking;

import android.app.Application;

import com.example.tourbooking.dao.PRM392RoomDatabase;
import com.example.tourbooking.helpler.SeedDatabase;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PRM392RoomDatabase db = PRM392RoomDatabase.getInstance(this);
        SeedDatabase seedDatabase = new SeedDatabase(this);
        seedDatabase.Initialize();
    }
}
