package com.example.tourbooking.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Vehicle")
public class Vehicle {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "vehicleName")
    @NotNull
    private String vehicleName;
    private String vehicleType;
    private boolean isActive;

    @Ignore
    public Vehicle(@NotNull String vehicleName, String vehicleType) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.isActive = true;
    }

    
    public Vehicle(@NotNull String vehicleName, String vehicleType, boolean isActive) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.isActive = isActive;
    }

    @Ignore
    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    
    
    

    public int getId() {
        return id;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(@NotNull String vehicleName) {
        this.vehicleName = vehicleName;
    }
    @Override
    public String toString() {
        return vehicleName; // This makes the ArrayAdapter display the vehicle name
    }
}
