package com.epicodus.gym.model;

public class PostModel {
    String mUsername;
    String mImage;
    public PostModel(){}
    public PostModel(String mUsername, String mImage) {
        this.mUsername = mUsername;
        this.mImage = mImage;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmImage() {
        return mImage;
    }
}
