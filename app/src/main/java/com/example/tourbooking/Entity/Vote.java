package com.example.tourbooking.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(foreignKeys ={
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"),
        @ForeignKey(entity = Tour.class, parentColumns = "id", childColumns = "tourId")
})
public class Vote {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "userId")
    private  int userId;
    
    private int tourId;
    private int votedNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getVotedNumber() {
        return votedNumber;
    }

    public void setVotedNumber(int votedNumber) {
        this.votedNumber = votedNumber;
    }

    public Vote(@NotNull int id, int userId, int tourId, int votedNumber) {
        this.id = id;
        this.userId = userId;
        this.tourId = tourId;
        this.votedNumber = votedNumber;
    }

    public Vote() {
    }

    public Vote(int userId, int tourId, int votedNumber) {
        this.userId = userId;
        this.tourId = tourId;
        this.votedNumber = votedNumber;
    }
}
