package com.db.terraform.data;

public class Product {
    private int product_id;
    private String name;
    private String type;
    private double price;

    public Product(int product_id, String name, String type, double price) {
        this.product_id = product_id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}