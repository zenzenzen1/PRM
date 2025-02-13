package com.example.demofirstapplication.activity.grid_view;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class Item implements Serializable {
    private int imageId;
    private String name;
    private double price;
    public Item(){}
    public Item(int imageId, String name, double price) {
        this.imageId = imageId;
        this.name = name;
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item{" +
                "imageId=" + imageId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
