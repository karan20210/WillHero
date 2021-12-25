package com.example.willherogui;

import java.nio.file.Paths;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TnT extends Obstacles{
    private boolean blast = false;
    private transient ImageView TnTImg;

    public boolean isBlast() {
        return blast;
    }

    public void setBlast(boolean blast) {
        this.blast = blast;
    }

    TnT(ImageView TnTImg)
    {
        super(TnTImg.getLayoutX(),TnTImg.getLayoutY());
        this.TnTImg=TnTImg;
    }

    @Override
    public void collide(Hero hero1){        
            
    }
}
