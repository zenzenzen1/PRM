package com.example.demofirstapplication.activity.list_view;

public class Phone {
    private int imageId;
    private String name;
    
    public Phone(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }
    public Phone(){}
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
}
