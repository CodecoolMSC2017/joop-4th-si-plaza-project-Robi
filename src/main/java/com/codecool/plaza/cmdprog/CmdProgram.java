package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CmdProgram {

    private List<Product> cart;
    private Scanner scan = new Scanner(System.in);
    SimpleDateFormat date = new SimpleDateFormat("mm-dd-yyyy");

    public CmdProgram() {
        cart = new ArrayList<Product>();
    }

    public void run() {
        PlazaImpl myPlaza;
        label:
        while (true) {
            System.out.println("There's no plaza.\nPress(1) to create one\nPress(2) to exit");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter the name of the Plaza:");
                    String plazaName = scan.nextLine();
                    myPlaza = new PlazaImpl(plazaName);
                    runPlazaMenu(myPlaza);
                    break label;
                case "2":
                    System.out.println("See you next time.");
                    System.exit(0);
                default:
                    System.out.println("Try (1) or (2)!");
                    break;
            }
        }
    }

    public void runPlazaMenu(PlazaImpl myPlaza) {
        while (true) {
            int number;
            ShopImpl myShop;
            String tempShop;
            String tempOwner;

            while (true) {
                System.out.println("\nWelcome to " + myPlaza.getName() + "!");
                System.out.println("Press\n" + "1: list all shops.\n" +
                        "2: add a new shop.\n" +
                        "3: remove an existing shop.\n" +
                        "4: enter a shop by name.\n" +
                        "5: open the plaza.\n" +
                        "6: close the plaza.\n" +
                        "7: check if the plaza is open or not.\n" +
                        "0: leave plaza.");
                String option = scan.nextLine();
                try {
                    number = Integer.parseInt(option);
                    break;
                } catch (NumberFormatException numex) {
                    System.out.println("Only numbers!");
                }
            }
            try {
                switch (number) {
                    case 1:
                        for (Shop shop : myPlaza.getShops()) {
                            System.out.println(shop.getOwner() + "'s " + shop.getName());
                        }
                        break;
                    case 2:
                        System.out.println("Enter the owner of the shop");
                        tempOwner = scan.nextLine();
                        System.out.println("Enter the name of the shop");
                        tempShop = scan.nextLine();
                        try {
                            myPlaza.addShop(new ShopImpl(tempShop, tempOwner));
                        } catch (ShopAlreadyExistsException sae) {
                            System.out.println(sae.getMessage());
                        }
                        break;
                    case 3:
                        for (Shop shop : myPlaza.getShops()) {
                            System.out.println(shop.getName());
                        }
                        System.out.println("Which shop do you want to remove?");
                        tempShop = scan.nextLine();
                        try {
                            myPlaza.removeShop(myPlaza.findShopByName(tempShop));
                            System.out.println(tempShop + " is removed from the plaza");
                        } catch (NoSuchShopException nsse) {
                            System.out.println(nsse.getMessage());
                        }
                        break;
                    case 4:
                        for (Shop shop : myPlaza.getShops()) {
                            System.out.println(shop.getName());
                        }
                        System.out.println("Which shop do you want to go in?");
                        tempShop = scan.nextLine();
                        try {
                            myShop = (ShopImpl) myPlaza.findShopByName(tempShop);
                            runShopMenu(myShop);
                            break;
                        } catch (NoSuchShopException nsse) {
                            System.out.println(nsse.getMessage());
                        }
                        break;
                    case 5:
                        myPlaza.open();
                        System.out.println("You have opened the Plaza\n");
                        break;
                    case 6:
                        myPlaza.close();
                        System.out.println("You have closed the Plaza\n");
                        break;
                    case 7:
                        if (myPlaza.isOpen()) {
                            System.out.println(myPlaza.getName() + " is open");
                        } else {
                            System.out.println(myPlaza.getName() + " is closed");
                        }
                        break;
                    case 0:
                        System.out.println("Goodbye");
                        System.exit(0);

                }
            } catch (PlazaIsClosedException pice) {
                System.out.println(pice.getMessage());
            }
        }
    }

    public void runShopMenu(ShopImpl myShop) {
        boolean running = true;
        while (running) {
            System.out.println("Welcome to " + myShop.getOwner() + "'s " + myShop.getName() + "!");
            System.out.println("1: list available products.\n" +
                    "2: find products by name.\n" +
                    "3: display the shop's owner.\n" +
                    "4: open the shop.\n" +
                    "5: close the shop.\n" +
                    "6: add new product to the shop.\n" +
                    "7: add existing products to the shop.\n" +
                    "8: buy a product by barcode.\n" +
                    "0: go back to plaza");
            String option = scan.nextLine();
            try {
                int subNum = Integer.parseInt(option);
                switch (subNum) {
                    case 1:
                        try {
                            System.out.println(myShop.toString());
                        } catch (IndexOutOfBoundsException ioobe) {
                            System.out.println("There's no item.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter the item name:");
                        String itemName = scan.nextLine();
                        try {
                            System.out.println(myShop.findByName(itemName).toString());
                        } catch (NoSuchProductException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        System.out.println("The owner of this shop is " + myShop.getOwner());
                        break;
                    case 4:
                        myShop.open();
                        System.out.println("You have opened the shop");
                        break;
                    case 5:
                        myShop.close();
                        System.out.println("You have closed the shop");
                        break;
                    case 6:
                        Product product = null;
                        boolean creatingProduct = true;
                        while (creatingProduct) {
                            String[] headerCloth = new String[] {"Enter the barcode: ", "Enter the manufacturer: ",
                                    "Enter the name: ", "Enter the material: ", "Enter the type: "};
                            String[] headerFood = new String[] {"Enter the barcode: ", "Enter the manufacturer: ",
                                    "Enter the name: ", "Enter the calorie: ", "Enter the date: (yyyy-mm-dd)"};
                            System.out.println("What kind of product do you want to create? (Cloth or Food)");
                            String clothOrFood = scan.nextLine().toLowerCase();
                            if (clothOrFood.equals("cloth")) {
                                String[] attributes = new String[headerCloth.length];
                                for (int i = 0; i < headerCloth.length; i++) {
                                    System.out.println(headerCloth[i]);
                                    attributes[i] = scan.nextLine().toLowerCase();
                                }
                                String tempManufacturer = attributes[1];
                                String tempName = attributes[2];
                                String tempMaterial = attributes[3];
                                String tempType = attributes[4];
                                try {
                                    long tempBarcode = Long.parseLong(attributes[0]);
                                    product = new ClothingProduct(tempBarcode, tempManufacturer, tempName, tempMaterial, tempType);
                                    creatingProduct = false;
                                } catch (NumberFormatException nfe) {
                                    System.out.println(nfe.getMessage());
                                }
                            } else if (clothOrFood.equals("food")) {
                                String[] attributes = new String[headerFood.length];
                                for (int i = 0; i < headerFood.length; i++) {
                                    System.out.println(headerFood[i]);
                                    attributes[i] = scan.nextLine().toLowerCase();
                                }
                                String tempManufacturer = attributes[1];
                                String tempName = attributes[2];
                                try {
                                    long tempBarcode = Long.parseLong(attributes[0]);
                                    int tempCalorie = Integer.parseInt(attributes[3]);
                                    Date tempDate = date.parse(attributes[4]);
                                    product = new FoodProduct(tempBarcode, tempManufacturer, tempName, tempCalorie, tempDate);
                                    creatingProduct = false;
                                } catch (ParseException pe) {
                                    System.out.println(pe.getMessage());
                                }
                            } else {
                                System.out.println("Please enter a valid option!");
                            }
                        }
                        System.out.println("Enter the quantity:");
                        String tempQuantityS = scan.nextLine();
                        System.out.println("Enter the price:");
                        String tempPriceS = scan.nextLine();
                        try {
                            int tempQuantity = Integer.parseInt(tempQuantityS);
                            float tempPrice = Float.parseFloat(tempPriceS);
                            myShop.addNewProduct(product, tempQuantity, tempPrice);
                        } catch (ProductAlreadyExistsException paee) {
                            System.out.println(paee.getMessage());
                        }
                        break;
                    case 0:
                        running = false;
                }
            } catch (ShopIsClosedException sice) {
                System.out.println(sice.getMessage());
            }
        }
    }
}
