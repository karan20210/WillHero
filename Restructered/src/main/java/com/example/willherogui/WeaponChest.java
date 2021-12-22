package com.example.willherogui;

import java.util.Random;

import javafx.scene.image.ImageView;

public class WeaponChest extends Chests
{
    private String providedWeapon;

    WeaponChest(ImageView weaponChest)
    {
        super(weaponChest.getLayoutX(),weaponChest.getLayoutY(),weaponChest);
        Random rand = new Random();
        int r = rand.nextInt(2);
        if(r==0)
            providedWeapon="Weapon1";
        else
            providedWeapon ="Weapon2";
    }

    public String getProvidedWeapon() {
        return providedWeapon;
    }

    

    @Override
    public void collide(Hero hero1){
        this.setOpen(true);
        switch(providedWeapon){
            case "Weapon1":
                if(hero1.equalWeapon1()==true){
                    hero1.upgradeCurrentWeapon();
                }

                else{
                    hero1.newWeapon(providedWeapon);
                }
            
            case "Weapon2":
                if(hero1.equalWeapon2()==true){
                    hero1.upgradeCurrentWeapon();
                }

                else{
                    hero1.newWeapon(providedWeapon);
                }
        }
    
        

        changeImage("src/main/resources/com/example/willherogui/Images/OpenWeaponChest.png");        

    }
}
