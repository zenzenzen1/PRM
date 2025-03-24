package com.example.tourbooking.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "Order", foreignKeys ={
        @ForeignKey(entity =  Status.class, parentColumns = "id", childColumns = "statusId"),
        @ForeignKey(entity = Tour.class, parentColumns = "id", childColumns = "tourId"),
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId")
            })
public class Order {
    
    public enum PaymentMethod {
        ZALOPAY(1, "Zalopay"),
        COD(2, "Cash on Departure");
        
        private final String displayPaymentMethod;
        private final int id;

        PaymentMethod(int id, String displayPaymentMethod) {
            this.displayPaymentMethod = displayPaymentMethod;
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public String getDisplayPaymentMethod() {
            return displayPaymentMethod;
        }
        
    }

    public Order() {
    }

    public Order(@NotNull int tourId, @NotNull int userId, @NotNull int numberOfPerson, @NotNull Date departureDay, @NotNull double totalFee, @NotNull int statusId, @NotNull Date orderDate, @NotNull Date endDate, String paymentMethod) {
        this.tourId = tourId;
        this.userId = userId;
        this.numberOfPerson = numberOfPerson;
        this.departureDay = departureDay;
        this.totalFee = totalFee;
        this.statusId = statusId;
        this.orderDate = orderDate;
        this.endDate = endDate;
        this.paymentMethod = paymentMethod;
    }

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    private int id;
    @NotNull
    @ColumnInfo(name = "tourId")
    private  int tourId;
    @NotNull
    @ColumnInfo(name = "userId")
    private  int userId;
    @NotNull
    @ColumnInfo(name = "numberOfPerson")
    private int numberOfPerson;
    @NotNull
    @ColumnInfo(name = "departureDay")
    private Date departureDay;
    @NotNull
    @ColumnInfo(name = "totalFee")
    private double totalFee;
    @NotNull
    @ColumnInfo(name = "statusId")
    private int statusId;
    @NotNull
    @ColumnInfo(name = "orderDate")
    private Date orderDate;

    @NotNull
    @ColumnInfo(name = "endDate")
    private Date endDate;
    
    private String paymentMethod;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    public Date getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(Date departureDay) {
        this.departureDay = departureDay;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @NotNull
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(@NotNull Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NotNull
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull Date endDate) {
        this.endDate = endDate;
    }


}
