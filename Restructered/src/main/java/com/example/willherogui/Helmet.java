package com.example.willherogui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Helmet extends GameObjects{

    private Weapon Weapon1;
    private Weapon Weapon2;
    private Pane helmetImg;

    Helmet(double x, double y)
    {
        super(x,y);
    }

    public void addWeapons(Weapon Weapon1,Weapon Weapon2){
        this.Weapon1=Weapon1;
        this.Weapon2=Weapon2;
    }

    public Weapon getWeapon1(){
        return Weapon1;
    }

    public Weapon getWeapon2(){
        return Weapon2;
    }

    public void NoVisibility(){
        Weapon1.setVisibility(true,0);
        Weapon2.setVisibility(true,0);
    }

   

   


    
}
