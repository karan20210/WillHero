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
    private boolean fired;

    Weapon(ImageView weap, int damage)
    {
        super(weap.getLayoutX(),weap.getLayoutY());
        this.damage = damage;
        this.weaponImg=weap;
        fired = false;
        level = 1;
        timesFired = 0;
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

//    public void shoot(Pane game){
//        Image image = new Image(Paths.get("src/main/resources/com/example/willherogui/Knife.jpg").toUri().toString());
//        // ImageView NewWeapon= new ImageView(image);
//
//        ImageView NewWeapon = new ImageView(weaponImg.getImage());
//        NewWeapon.setFitWidth(weaponImg.getFitWidth());
//        NewWeapon.setFitHeight(weaponImg.getFitHeight());
//        NewWeapon.setLayoutX(weaponImg.getLayoutX());
//        NewWeapon.setLayoutY(weaponImg.getLayoutY());
//        NewWeapon.setVisible(true);
//
//        game.getChildren().add(NewWeapon);
//        NewWeapon.setViewOrder(2);
//        // System.out.println(NewWeapon.getViewOrder());
//        // if(game.getChildren().contains(NewWeapon)){
//        //     System.out.println("lol");
//
//        // }
//
//
//
//
//
//        TranslateTransition move = new TranslateTransition();
//        move.setDuration(Duration.millis(400));
//        move.setNode(NewWeapon);
//        move.setByX(+200);
//        move.setCycleCount(1);
//        move.play();
//
//        // game.getChildren().remove(NewWeapon);
//
//    };

    public void shoot(Hero h)
    {
        if(!fired)
        {
            double x = weaponImg.getTranslateX();
            double y = weaponImg.getTranslateY();
            TranslateTransition move = new TranslateTransition();
            move.setDuration(Duration.millis(400));
            move.setNode(weaponImg);
            move.setByX(+200);
            move.setCycleCount(1);
            move.play();
            fired = true;
            timesFired++;

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            TranslateTransition moveBack = new TranslateTransition();
                            moveBack.setDuration(Duration.millis(10));
                            moveBack.setNode(weaponImg);
                            moveBack.setByX(-200);
                            moveBack.setCycleCount(1);
                            moveBack.play();
//                            weaponImg.setTranslateX(x);
//                            weaponImg.setTranslateY(y);
                            fired = false;

                        }
                    }, 500
            );
        }


    }
}
