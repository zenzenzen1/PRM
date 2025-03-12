package com.example.tourbooking.repository;

import android.content.Context;

import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.dao.PRM392RoomDatabase;
import com.example.tourbooking.dao.StatusDao;

import java.util.List;

public class StatusRepository {
    private StatusDao statusDao;
    public  StatusRepository(Context context){
        statusDao = PRM392RoomDatabase.getInstance(context).statusDao();
    }
    public void createStatus(Status status){
        statusDao.insert(status);
    }

    public void updateStatus(Status status){
        statusDao.update(status);
    }

    public Status getStatus(int statusId){
        return  statusDao.select(statusId);
    }

    public List<Status> getAllStatus(){
        return  statusDao.selectAll();
    }
}
