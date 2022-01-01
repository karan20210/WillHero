package com.example.willherogui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

public abstract class Orcs extends GameObjects implements Movable, Collision
{
    private int health;
    private boolean alive;
    private transient ImageView orcImg;
    private transient TranslateTransition orcJump;
    

    Orcs(double x, double y,ImageView orcImg)
    {
        super(x,y);
        health = 100;
        alive = true;
        this.orcImg=orcImg;
    }

    Orcs(double x, double y, ImageView orcImg, int h)
    {
        super(x,y);
        health = h;
        alive = true;
        this.orcImg=orcImg;
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

    public void shot(int damage)
    {
        health = health - (damage/20);
            
        if(health<=20){
            setDamageImg2(orcImg);
        }
        else if(health<=70){
            setDamageImg1(orcImg);
        }
        
        if(health <= 0)
            alive = false;
    }

    public abstract void setDamageImg1(ImageView orcImg);
    public abstract void setDamageImg2(ImageView orcImg);

    public void resumeJump(){
        orcJump.play();
    }

    public void pauseJump(){
        orcJump.pause();
        
    }

    @Override
    public void startMove(){
        orcJump = new TranslateTransition();
        orcJump.setDuration(Duration.millis(1000));
        orcJump.setNode(this.orcImg);
        orcJump.setByY(-40);
        orcJump.setCycleCount(Animation.INDEFINITE);
        orcJump.setAutoReverse(true);
        orcJump.setInterpolator(Interpolator.LINEAR);
        orcJump.play();

    }

    @Override
    public void fall(){
        if(orcJump!=null){
            orcJump.stop();
        }
        
        TranslateTransition fall = new TranslateTransition();
        fall.setDuration(Duration.millis(400));
        fall.setNode(orcImg);
        fall.setByY(400);
        fall.setCycleCount(1);
        fall.setInterpolator(Interpolator.LINEAR);
        fall.play();

    }

    @Override
    public void collide(Hero hero){

        TranslateTransition orcmoveRight = new TranslateTransition();
        orcmoveRight.setDuration(Duration.millis(50));
        orcmoveRight.setNode(orcImg);
        orcmoveRight.setByX(+30);
        orcmoveRight.setCycleCount(1);
        orcmoveRight.play();

        // TranslateTransition orcBoxmoveRight = new TranslateTransition();
        // orcBoxmoveRight.setDuration(Duration.millis(50));
        // orcBoxmoveRight.setNode(orcBoxesInGame.get(i));
        // orcBoxmoveRight.setByX(+30);
        // orcBoxmoveRight.setCycleCount(1);
        // orcBoxmoveRight.play();

    }

    public void collideWithOrc(){
        TranslateTransition orcmoveRight = new TranslateTransition();
        orcmoveRight.setDuration(Duration.millis(50));
        orcmoveRight.setNode(orcImg);
        orcmoveRight.setByX(+30);
        orcmoveRight.setCycleCount(1);
        orcmoveRight.play();

    }

}
