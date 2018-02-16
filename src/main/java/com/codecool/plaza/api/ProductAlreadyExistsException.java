package com.codecool.plaza.api;

public class ProductAlreadyExistsException extends Exception {

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
