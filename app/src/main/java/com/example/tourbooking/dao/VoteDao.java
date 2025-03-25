package com.example.tourbooking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tourbooking.Entity.Vote;

import java.util.List;

@Dao
public interface VoteDao {
    @Insert
    void insert(Vote vote);
    @Update
    void update(Vote vote);
    @Query("SELECT * FROM Vote WHERE userId =:userId AND tourId =:tourId")
    Vote getVoteByUserIdTourId(int userId, int tourId);
    
    @Query("SELECT * FROM Vote WHERE tourId =:tourId")
    List<Vote> getVoteByTourId(int tourId);
    
    @Query("SELECT SUM(votedNumber) FROM Vote WHERE tourId =:tourId")
    int getTotalVoteByTourId(int tourId);
}
