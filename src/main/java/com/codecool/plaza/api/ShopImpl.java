package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopImpl implements Shop {

    private String name;
    private String owner;
    private Map<Long, ShopEntryImpl> products;
    private boolean open;

    public ShopImpl(String name, String owner) {
        this.name = name;
        this.owner = owner;
        products = new HashMap<Long, ShopImpl.ShopEntryImpl>();
    }

    @Override
    public List<Product> getProducts() throws ShopIsClosedException {

        List<Product> tempList = new ArrayList<Product>();

        if (isOpen()) {
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                ShopEntryImpl value = entry.getValue();
                Product temp = value.getProduct();
                tempList.add(temp);
            }
            return tempList;
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public Product findByName(String name) throws NoSuchProductException, ShopIsClosedException {

        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                ShopEntryImpl value = entry.getValue();
                Product tempProduct = value.getProduct();
                if (tempProduct.getName().equals(name)){
                    return tempProduct;
                }
            }
            throw new NoSuchProductException("There is no product with this name in our shop!");
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public float getPrice(long barcode) throws NoSuchProductException, ShopIsClosedException {

        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key){
                    return value.getPrice();
                }
            }
            throw new NoSuchProductException("There is no product with this name in our shop!");
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public boolean hasProduct(long barcode) throws ShopIsClosedException {

        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                if (barcode == key){
                    return true;
                }
            }
            return false;
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {

        if (isOpen()){
            if (hasProduct(product.getBarcode())){
                throw new ProductAlreadyExistsException("This product is already exist in this shop!");
            } else {
                products.put(product.getBarcode(), new ShopEntryImpl(product, quantity, price));
            }
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {

        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key){
                    value.increaseQuantity(quantity);
                }
            }
            throw new NoSuchProductException("There is no product with this name in our shop!");
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public Product buyProduct(long barcode) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {

        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key){
                    if (value.getQuantity() != 0) {
                        value.decreaseQuantity(1);
                    } else {
                        throw new OutOfStockException("This product is currently out of stock!");
                    }
                }
            }
            throw new NoSuchProductException("There is no product with this name in our shop!");
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {

        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key){
                    if (value.getQuantity() != 0) {
                        value.decreaseQuantity(quantity);
                    } else {
                        throw new OutOfStockException("This product is currently out of stock!");
                    }
                }
            }
            throw new NoSuchProductException("There is no product with this name in our shop!");
        } else {
            throw new ShopIsClosedException("Shop is closed!");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOwner() {
        return owner;
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

    @Override
    public String toString() {

        String allProducts = "";

        allProducts += "ClothingProducts: ";
        for (Map.Entry<Long, ShopEntryImpl> entry : products.entrySet()) {
            if (entry.getValue().getProduct() instanceof ClothingProduct) {
                allProducts += "\n" + entry.getValue().getProduct().toString() + " | quantity: " + entry.getValue().getQuantity() + " | price: " + entry.getValue().getPrice() + " ft";
            }
        }
        allProducts += "\nFoodProducts: ";
        for (Map.Entry<Long, ShopEntryImpl> entry : products.entrySet()) {
            if (entry.getValue().getProduct() instanceof FoodProduct) {
                allProducts += "\n" + entry.getValue().getProduct().toString() + " | quantity: " + entry.getValue().getQuantity() + " | price: " + entry.getValue().getPrice() + " ft";
            }

        }
        return allProducts;
    }

    private class ShopEntryImpl {

        private Product product;
        private int quantity;
        private float price;

        public ShopEntryImpl(Product product, int quantity, float price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public void increaseQuantity(int amount){
            quantity += amount;
        }

        public void decreaseQuantity(int amount){
            quantity -= amount;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ShopEntryImpl{" +
                    "product=" + product.toString() +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }
}
