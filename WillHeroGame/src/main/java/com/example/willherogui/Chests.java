package com.example.willherogui;

public abstract class Chests extends GameObjects
{
    private boolean open;

    Chests(double x, double y)
    {
        super(x,y);
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
