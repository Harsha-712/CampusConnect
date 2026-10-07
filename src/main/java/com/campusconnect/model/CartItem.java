package com.campusconnect.model;

public class CartItem implements java.io.Serializable {
    private int id;
    private String name;
    private double price;
    private int qty;
    private double line;

    public CartItem() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getLine() {
        return line;
    }

    public void setLine(double line) {
        this.line = line;
    }

}
