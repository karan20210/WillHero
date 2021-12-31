package com.example.willherogui;

import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;



public class Knife extends Weapon
{
    Knife(ImageView knife, Image img, Pane helmet)
    {
        super(knife,img,40, helmet);
    }

    @Override
    public void setWeaponUprgraded1(ImageView i){
        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/KnifeUpgraded2.png").toUri().toString()));
    }

    @Override
    public void setWeaponUprgraded2(ImageView i){
        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/KnifeUpgraded1.png").toUri().toString()));
    }

   
}
