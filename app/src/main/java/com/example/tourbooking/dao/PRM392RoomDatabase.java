package com.example.tourbooking.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tourbooking.Entity.Category;
import com.example.tourbooking.Entity.Converters;
import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.Entity.Role;
import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.Entity.Tour;
import com.example.tourbooking.Entity.User;
import com.example.tourbooking.Entity.Vehicle;

@Database(entities = {Category.class, Order.class,
        Role.class, Status.class, Tour.class, User.class, Vehicle.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class PRM392RoomDatabase extends RoomDatabase {
    private static final String DB_NAME = "PRM392Database";
    private static PRM392RoomDatabase INSTANCE;

    public abstract com.example.tourbooking.dao.CategoryDao categoryDao();
    public abstract com.example.tourbooking.dao.UserDao userDao();
    public abstract com.example.tourbooking.dao.OrderDao orderDao();
    public abstract com.example.tourbooking.dao.RoleDao roleDao();
    public abstract com.example.tourbooking.dao.StatusDao statusDao();
    public abstract com.example.tourbooking.dao.TourDao tourDao();
    public abstract com.example.tourbooking.dao.VehicleDao vehicleDao();

    public static synchronized PRM392RoomDatabase getInstance(Context context){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                PRM392RoomDatabase.class, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigrationFrom(5)
                        .enableMultiInstanceInvalidation()
                        .build();
        }
        return INSTANCE;
    }
}
