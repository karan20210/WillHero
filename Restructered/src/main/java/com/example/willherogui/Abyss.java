package com.example.willherogui;


public class Abyss extends GameObjects implements Collision
{
    Abyss(double x, double y)
    {
        super(x,y);
    }

    @Override
    public void collide(Hero hero){
            hero.setAlive(false);
            hero.fall();
    }

    public void collide(Orcs orc){
        orc.setAlive(false);
        orc.fall();
    }
}
