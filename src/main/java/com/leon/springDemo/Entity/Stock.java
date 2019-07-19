package com.leon.springDemo.Entity;

public class Stock {
    private final String id;
    private final String name;
    private final double lastestPrice;
    public Stock(String sid){
        this.id = sid;
        this.name = null;
        this.lastestPrice =0;
    }

    public String getId() {
        return id;
    }
    public String getName(){
        return this.name;
    }
    public double getLastestPrice(){
        return this.lastestPrice;
    }

}
