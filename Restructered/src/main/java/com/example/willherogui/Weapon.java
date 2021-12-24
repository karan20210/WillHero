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
    private transient ImageView weaponImg;

    Weapon(ImageView weap, int damage)
    {
        super(weap.getLayoutX(),weap.getLayoutY());
        this.damage = damage;
        this.weaponImg=weap;
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
        weaponImg.setOpacity(opacity);
        weaponImg.setDisable(power);

        
    }

    public void shoot(Pane game){
        Image image = new Image(Paths.get("src/main/resources/com/example/willherogui/Knife.jpg").toUri().toString()); 
        // ImageView NewWeapon= new ImageView(image);

        ImageView NewWeapon = new ImageView(weaponImg.getImage());
        NewWeapon.setFitWidth(weaponImg.getFitWidth());
        NewWeapon.setFitHeight(weaponImg.getFitHeight());
        NewWeapon.setLayoutX(weaponImg.getLayoutX());
        NewWeapon.setLayoutY(weaponImg.getLayoutY());
        NewWeapon.setVisible(true);
        
        game.getChildren().add(NewWeapon);
        NewWeapon.setViewOrder(2);
        // System.out.println(NewWeapon.getViewOrder());
        // if(game.getChildren().contains(NewWeapon)){
        //     System.out.println("lol");

        // }

        
        
      

        TranslateTransition move = new TranslateTransition();
        move.setDuration(Duration.millis(400));
        move.setNode(NewWeapon);
        move.setByX(+200);
        move.setCycleCount(1);
        move.play();

        // game.getChildren().remove(NewWeapon);
        
    };

}
