package com.example.tourbooking.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Status")
public class Status {
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    private int id;
    @NotNull
    @ColumnInfo(name = "statusName")
    private String statusName;
    @NotNull
    @ColumnInfo(name = "statusDesc")
    private String statusDesc;

    public Status() {
    }

    public Status(@NotNull String statusName, @NotNull String statusDesc) {
        this.statusName = statusName;
        this.statusDesc = statusDesc;
    }

    public Status(int id, @NotNull String statusName, @NotNull String statusDesc) {
        this.id = id;
        this.statusName = statusName;
        this.statusDesc = statusDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(@NotNull String statusName) {
        this.statusName = statusName;
    }

    @NotNull
    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(@NotNull String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public enum StatusEnum {
        COMPLETED(1, "Completed")
        , CANCELLED(2, "Cancelled")
        , PENDING(3, "Pending")
        , ACCEPTED(4, "Accepted")
        , REJECTED(5, "Rejected")
        ;
        private final int id;
        private final String statusName;
        
        public static String GetStatusNameById(int id){
            for(StatusEnum se : StatusEnum.values()){
                if(se.id == id){
                    return se.statusName;
                }
            }
            return null;
        }
        
        StatusEnum(int id, String statusName) {
            this.id = id;
            this.statusName = statusName;
        }

        public int getId() {
            return id;
        }

        public String getStatusName() {
            return statusName;
        }
    }
}
