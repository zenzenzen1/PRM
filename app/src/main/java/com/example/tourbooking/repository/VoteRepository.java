package com.example.tourbooking.repository;

import android.content.Context;

import com.example.tourbooking.Entity.Vote;
import com.example.tourbooking.dao.PRM392RoomDatabase;
import com.example.tourbooking.dao.VoteDao;

public class VoteRepository {
    private final VoteDao voteDao;
    public  VoteRepository(Context context){
        voteDao = PRM392RoomDatabase.getInstance(context).voteDao();
    }
    
    public void insert(Vote vote){
        voteDao.insert(vote);
    }
    public void update(Vote vote){
        voteDao.update(vote);
    }
    public Vote getVoteByUserIdTourId(int userId, int tourId){
        return voteDao.getVoteByUserIdTourId(userId, tourId);
    }
}
