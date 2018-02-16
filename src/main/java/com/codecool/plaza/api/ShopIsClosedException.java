package com.codecool.plaza.api;

public class ShopIsClosedException extends Exception {

    public ShopIsClosedException(String message) {
        super(message);
    }
}
