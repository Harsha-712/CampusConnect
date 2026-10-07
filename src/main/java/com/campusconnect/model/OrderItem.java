package com.campusconnect.model;

public class OrderItem implements java.io.Serializable {
    private String name;
    private int qty;
    private double price;
    private double line;

    public OrderItem() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLine() {
        return line;
    }

    public void setLine(double line) {
        this.line = line;
    }

}
