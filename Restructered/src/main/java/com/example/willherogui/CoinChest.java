package com.example.willherogui;

import javafx.scene.image.ImageView;

public class CoinChest extends Chests
{
    private int amount;

    CoinChest(ImageView Chest)
    {
        super(Chest.getLayoutX(),Chest.getLayoutY(),Chest);
        amount = 10;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override

    public void collide(Hero hero1){
        this.setOpen(true);
        hero1.addCoins(this.getAmount());
        changeImage("src/main/resources/com/example/willherogui/Images/OpenCoinChest.png");
    }

}
