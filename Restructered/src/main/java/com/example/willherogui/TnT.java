package com.example.willherogui;

public class TnT extends Obstacles{
    private boolean blast = false;

    public boolean isBlast() {
        return blast;
    }

    public void setBlast(boolean blast) {
        this.blast = blast;
    }

    TnT(double x, double y)
    {
        super(x,y);
    }
}
