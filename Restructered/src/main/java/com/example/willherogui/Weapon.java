package com.example.willherogui;

import java.io.FileInputStream;
import java.nio.file.Paths;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Weapon extends GameObjects
{
    private int level;
    private int damage;
    private int timesFired;
    private transient ImageView weaponImg;
    private transient Pane helmet;
    private boolean fired;
    private transient Image img;

    Weapon(ImageView weap, Image img,int damage, Pane h)
    {
        super(weap.getLayoutX(),weap.getLayoutY());
        this.damage = damage;
        this.weaponImg=weap;
        fired = false;
        level = 1;
        timesFired = 0;
        this.img=img;
        this.helmet = h;
    }

    public ImageView getImageView(){
        return this.weaponImg;
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
        timesFired = 0;
        level++;
        damage+=10;
    }

    public void setVisibility(boolean power,double opacity){
        weaponImg.setOpacity(opacity);
        weaponImg.setDisable(power);

        
    }

    public void setHelmet(Pane helmet) {
        this.helmet = helmet;
    }

    public Pane getHelmet() {
        return helmet;
    }

    public void shoot(Hero h)
    {
        if(!fired)
        {
//            TranslateTransition move = new TranslateTransition();
//            move.setDuration(Duration.millis(400));
//            move.setNode(weaponImg);
//            move.setByX(+200);
//            move.setCycleCount(1);
//            move.play();
//            fired = true;
//            timesFired++;

            TranslateTransition moveHelmet = new TranslateTransition();
            moveHelmet.setDuration(Duration.millis(400));
            moveHelmet.setNode(helmet);
            moveHelmet.setByX(+200);
            moveHelmet.setCycleCount(1);
            moveHelmet.play();

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
//                            TranslateTransition moveBack = new TranslateTransition();
//                            moveBack.setDuration(Duration.millis(10));
//                            moveBack.setNode(weaponImg);
//                            moveBack.setByX(-200);
//                            moveBack.setCycleCount(1);
//                            moveBack.play();

                            TranslateTransition moveBackHelmet = new TranslateTransition();
                            moveBackHelmet.setDuration(Duration.millis(10));
                            moveBackHelmet.setNode(helmet);
                            moveBackHelmet.setByX(-200);
                            moveBackHelmet.setCycleCount(1);
                            moveBackHelmet.play();
                            fired = false;
                        }
                    }, 400
            );
        }


    }

   
}
