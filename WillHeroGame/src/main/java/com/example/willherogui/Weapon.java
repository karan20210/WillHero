package com.example.willherogui;

public abstract class Weapon extends GameObjects
{
    private int level;
    private int damage;

    Weapon(double x, double y, int damage)
    {
        super(x,y);
        this.damage = damage;
        level = 1;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void upgrade()
    {
        level++;
    }

    public abstract void shoot();

}
