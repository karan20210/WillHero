package com.example.willherogui;

public class CoinChest extends Chests
{
    private int amount;

    CoinChest(double x, double y)
    {
        super(x,y);
        amount = 10;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
