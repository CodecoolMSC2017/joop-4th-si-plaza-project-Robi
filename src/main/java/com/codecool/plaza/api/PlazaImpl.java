package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza {

    private List<Shop> shops;
    private String name;
    private boolean open;

    public PlazaImpl(String name) {
        shops = new ArrayList<Shop>();
        this.name = name;
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {

        if (isOpen()) {
            return shops;
        } else {
            throw new PlazaIsClosedException("Plaza is closed!");
        }
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {

        if (isOpen()){
            for (Shop iterateShop : shops){
                if (iterateShop.getName().equals(shop.getName())){
                    throw new ShopAlreadyExistsException("This shop is already exist in our plaza!");
                }
            }
            shops.add(shop);
        } else {
            throw new PlazaIsClosedException("Plaza is closed!");
        }
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {

        if (isOpen()){
            if (shops.contains(shop)){
                shops.remove(shop);
            } else {
                throw new NoSuchShopException("The shop you want to delete is not exist in our plaza!");
            }
        } else {
            throw new PlazaIsClosedException("Plaza is closed!");
        }
    }

    @Override
    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {

        if (isOpen()){
            for (Shop iterateShop : shops){
                if (name.equals(iterateShop.getName())){
                    return iterateShop;
                }
            }
            throw new NoSuchShopException("There is no shop with this name in our plaza!");
        } else {
            throw new PlazaIsClosedException("Plaza is closed!");
        }
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void open() {
        open = true;
    }

    @Override
    public void close() {
        open = false;
    }

    public String getName() {
        return name;
    }
}
