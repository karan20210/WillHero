package com.example.willherogui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Coin extends GameObjects implements Collision {
    private boolean collected;
    private ImageView coinImg;

    Coin(ImageView coinImg)
    {
        super(coinImg.getLayoutX(),coinImg.getLayoutY());
        collected = false;
        this.coinImg=coinImg;
    }

    public boolean isCollected() {
        return collected;
    }

    public void rotate(){
        RotateTransition rotate = new RotateTransition(Duration.millis(4000), coinImg);
        rotate.setByAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.play();
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public void collide(Hero hero){
        if(isCollected())
            {
                hero.addCoins(1);
                setCollected(true);
                coinImg.setOpacity(0);
                coinImg.setDisable(true);
            }
    }
}
