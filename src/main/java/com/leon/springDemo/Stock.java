package com.leon.springDemo;

public class Stock {
    private final String id;
    private final String name;
    private final double lastestPrice;
    public Stock(String sid){
        this.id = sid;
        this.name = null;
        this.lastestPrice =0;
    }
    public String getName(){
        return this.name;
    }
    public double getLastestPrice(){
        return this.lastestPrice;
    }

}
