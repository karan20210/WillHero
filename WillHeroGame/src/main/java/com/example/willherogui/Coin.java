package com.example.willherogui;

import javafx.scene.image.Image;

public class Coin extends GameObjects {
    private boolean collected;

    Coin(double x, double y)
    {
        super(x,y);
        collected = false;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
