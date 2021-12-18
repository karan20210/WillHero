package com.example.willherogui;

import java.util.Random;

public class WeaponChest extends Chests
{
    private Weapon providedWeapon;
    WeaponChest(double x, double y)
    {
        super(x,y);
        Random rand = new Random();
        int r = rand.nextInt(2);
        if(r==0)
            providedWeapon = new Knife(x, y);
        else
            providedWeapon = new Axe(x,y);
    }

    public Weapon getProvidedWeapon() {
        return providedWeapon;
    }

    public void setProvidedWeapon(Weapon providedWeapon) {
        this.providedWeapon = providedWeapon;
    }
}
