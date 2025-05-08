package com.db.terraform.data;

public class Product {
    private int product_id;
    private String name;
    private String type;
    private double price;
    private String created;
    private String updated;

    public Product(int product_id, String name, String type, double price, String created, String updated) {
        this.product_id = product_id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.created = created;
        this.updated = updated;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}