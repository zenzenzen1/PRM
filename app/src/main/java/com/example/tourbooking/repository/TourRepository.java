package com.example.tourbooking.repository;

import android.content.Context;

import com.example.tourbooking.Entity.Tour;
import com.example.tourbooking.Entity.Vote;
import com.example.tourbooking.dao.PRM392RoomDatabase;
import com.example.tourbooking.dao.TourDao;
import com.example.tourbooking.dao.VoteDao;

import java.util.List;

public class TourRepository {
    private TourDao tourDao;
    private VoteDao voteDao;
    private PRM392RoomDatabase db;

    public TourRepository(Context context) {
        db = PRM392RoomDatabase.getInstance(context);
        tourDao = db.tourDao();
        voteDao = db.voteDao();
    }

//    public TourRepository(Context context) {
//        tourDao = PRM392RoomDatabase.getInstance(context).tourDao();
//        PRM392RoomDatabase db = PRM392RoomDatabase.getInstance(context);
//        List<Tour> tours = db.tourDao().selectAll();
//    }

    public void createTour(Tour tour) {
        db.runInTransaction(() -> {
            tourDao.insert(tour);
        });
    }

    public void updateTour(Tour tour) {
        db.runInTransaction(() -> {
            tourDao.update(tour);
        });
    }

    public Tour getTour(int tourId) {
        return tourDao.select(tourId);
    }

    public List<Tour> getAllTour() {
        return tourDao.selectAll();
    }
    
    public List<Tour> getToursByCategoryId(int categoryId) {
        return tourDao.getToursByCategoryId(categoryId);
    }
    public boolean updateTourVote(int userId, int tourId, int voteValue) {
        db.runInTransaction(() -> {
            Tour tour = tourDao.select(tourId);
            if (tour != null) {
                Vote vote = voteDao.getVoteByUserIdTourId(userId, tourId);
                if(vote != null){
                    vote.setVotedNumber(voteValue);
                    voteDao.update(vote);
                    int totalVote = voteDao.getTotalVoteByTourId(tourId);
                    tour.setVoteScore(totalVote / tour.getVotedNumber());
                }else {
                    tour.setVotedNumber(tour.getVotedNumber() + 1);
                    voteDao.insert(new Vote(userId, tourId, voteValue));
                    int totalVote = voteDao.getTotalVoteByTourId(tourId);
                    tour.setVoteScore(totalVote / (tour.getVotedNumber()));
                }
                tourDao.update(tour);
            }
        });
        return true;
    }
}
