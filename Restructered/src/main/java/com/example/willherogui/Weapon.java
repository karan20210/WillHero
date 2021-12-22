package com.example.willherogui;

import javafx.scene.image.ImageView;

public abstract class Weapon extends GameObjects
{
    private int level;
    private int damage;
    private ImageView weapon;

    Weapon(ImageView weap, int damage)
    {
        super(weap.getLayoutX(),weap.getLayoutY());
        this.damage = damage;
        this.weapon=weap;
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
        damage+=10;
    }

    public void setVisibility(boolean power,double opacity){
        weapon.setOpacity(opacity);
        weapon.setDisable(power);

        
    }

    public abstract void shoot();

}
