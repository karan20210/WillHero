package com.example.willherogui;

import java.io.Serializable;

public abstract class GameObjects implements Serializable {
    private double x, y;
    protected static final long serialversionUID =
            129348938L;
    GameObjects(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
