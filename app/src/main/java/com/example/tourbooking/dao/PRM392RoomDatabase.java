package com.example.tourbooking.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tourbooking.Entity.Category;
import com.example.tourbooking.Entity.Converters;
import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.Entity.Role;
import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.Entity.Tour;
import com.example.tourbooking.Entity.User;
import com.example.tourbooking.Entity.Vehicle;
import com.example.tourbooking.Entity.Vote;

@Database(entities = {Category.class, Order.class,
        Role.class, Status.class, Tour.class, User.class, Vehicle.class, Vote.class}, version = 7)
@TypeConverters({Converters.class})
public abstract class PRM392RoomDatabase extends RoomDatabase {
    public static final String DB_NAME = "PRM392Database";
    private static PRM392RoomDatabase INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract UserDao userDao();
    public abstract OrderDao orderDao();
    public abstract VoteDao voteDao();
    public abstract RoleDao roleDao();
    public abstract StatusDao statusDao();
    public abstract TourDao tourDao();
    public abstract VehicleDao vehicleDao();

    public static synchronized PRM392RoomDatabase getInstance(Context context){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                PRM392RoomDatabase.class, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigrationFrom(6)
                        .enableMultiInstanceInvalidation()
                        .build();
        }
        return INSTANCE;
    }
}
