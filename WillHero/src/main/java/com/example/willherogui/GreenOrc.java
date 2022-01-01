package com.example.willherogui;

import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GreenOrc extends Orcs{

    GreenOrc(ImageView orc)
    {
        super(orc.getLayoutX(),orc.getLayoutY(),orc);
        orc.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/GreenOrc.png").toUri().toString()));
    }
    
    @Override
    public void setDamageImg1(ImageView orcImg) {
        orcImg.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/GreenOrcDamage1.png").toUri().toString()));
        
    }
    @Override
    public void setDamageImg2(ImageView orcImg) {
        orcImg.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/GreenOrcDamage2.png").toUri().toString()));
        
    }
}
