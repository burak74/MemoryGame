package com.example.memoryyeni;

import android.widget.ImageButton;

import java.util.ArrayList;

public class CardGame {
    private static CardGame singleton;
    private ArrayList<ImageButton> imageList;
    private ArrayList<String> imageIds;
    private ArrayList<String> highscores;
    private String username;
    private int coulmn;
    private int row;
    private boolean timer = false;
    private int timeRemaining;
    private int currentLevel;
    private String gameLevel;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCoulmn() {
        return coulmn;
    }

    public void setCoulmn(int coulmn) {
        this.coulmn = coulmn;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isTimer() {
        return timer;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

    public ArrayList<ImageButton> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<ImageButton> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(ArrayList<String> imageIds) {
        this.imageIds = imageIds;
    }

    public ArrayList<String> getHighscores() {
        return highscores;
    }

    public void setHighscores(ArrayList<String> highscores) {
        this.highscores = highscores;
    }

    public static CardGame getInstance() {
        if(singleton == null){
            singleton = new CardGame();
        }
        return singleton;
    }
}
