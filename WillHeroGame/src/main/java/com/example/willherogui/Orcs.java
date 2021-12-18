package com.example.willherogui;

public abstract class Orcs extends GameObjects
{
    private int health;
    private boolean alive;

    Orcs(double x, double y)
    {
        super(x,y);
        health = 100;
        alive = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void shot(Weapon w)
    {
        health = health - w.getDamage();
        if(health <= 0)
            alive = false;
    }

}
