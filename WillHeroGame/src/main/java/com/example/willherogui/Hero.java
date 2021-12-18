package com.example.willherogui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Hero extends GameObjects
{
    private boolean alive;
    private int currentCoins;
    private int score;

    Hero(double x, double y)
    {
        super(x,y);
        alive = true;
        currentCoins = 0;
        score = 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getCurrentCoins() {
        return currentCoins;
    }

    public void setCurrentCoins(int currentCoins) {
        this.currentCoins = currentCoins;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addCoins(int i)
    {
        currentCoins += i;
    }
}
