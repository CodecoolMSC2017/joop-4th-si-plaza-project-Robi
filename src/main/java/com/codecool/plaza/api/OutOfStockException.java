package com.codecool.plaza.api;

public class OutOfStockException extends Exception {

    public OutOfStockException(String message) {
        super(message);
    }
}
