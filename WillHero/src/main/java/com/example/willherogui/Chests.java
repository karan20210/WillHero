package com.example.willherogui;

import java.nio.file.Paths;

import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Chests extends GameObjects implements Collision
{
    private boolean open;
    private transient ImageView chest;

    Chests(double x, double y,ImageView Chest)
    {
        super(x,y);
        open = false;
        this.chest=Chest;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void changeImage(String path){
        chest.setImage(new Image(Paths.get(path).toUri().toString()));
        Glow glow = new Glow();
        glow.setLevel(1);
        chest.setEffect(glow);
    }

}
