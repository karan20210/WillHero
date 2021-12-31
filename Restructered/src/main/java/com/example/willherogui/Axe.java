package com.example.willherogui;

import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Axe extends Weapon
{
    Axe(ImageView axe, Image img, Pane helmet)
    {
        super(axe,img,60, helmet);
    }

    @Override
    public void setWeaponUprgraded1(ImageView i){
        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/AxeUpgraded1.png").toUri().toString()));
    }

    @Override
    public void setWeaponUprgraded2(ImageView i){
        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/AxeUpgraded2.png").toUri().toString()));
    }

   
}
