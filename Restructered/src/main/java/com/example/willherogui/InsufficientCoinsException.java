package com.example.willherogui;

public class InsufficientCoinsException extends Exception{

    InsufficientCoinsException(String msg)
    {
        super(msg);
    }
}
