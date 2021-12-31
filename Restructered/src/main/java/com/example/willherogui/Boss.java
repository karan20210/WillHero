package com.example.willherogui;
import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends Orcs{
    Boss(ImageView boss)
    {
        super(boss.getLayoutX(),boss.getLayoutY(),boss, 200);
    }

    @Override
    public void setDamageImg1(ImageView orcImg) {
        orcImg.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/BossDamage1.png").toUri().toString()));
        
    }
    @Override
    public void setDamageImg2(ImageView orcImg) {
        orcImg.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/BossDamage2.png").toUri().toString()));
        
    }
    
}




