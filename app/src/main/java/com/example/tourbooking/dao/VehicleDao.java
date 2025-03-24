package com.example.tourbooking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tourbooking.Entity.Vehicle;

import java.util.List;
@Dao
public interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Vehicle vehicle);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Vehicle vehicle);

    @Query("SELECT * FROM Vehicle v WHERE v.id =:vehicle_id")
    Vehicle select(int vehicle_id);

    @Query("SELECT * FROM VEHICLE ")
    List<Vehicle> selectAll();

    @Query("DELETE FROM Vehicle")
    void deleteAll();
}
