package com.db.terraform.data;

public class Customer {
    private int customer_id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String created;
    private String updated;

    public Customer(int customer_id, String name, String address, String phone, String email, String created, String updated) {
        this.customer_id = customer_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.created = created;
        this.updated = updated;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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