package com.leon.springDemo;

import java.util.Date;

public class StockDividend {
    private String stockId;
    private String stockName;
    private int reportYear;
    private Date registDate;
    private Date dividendDate;

    public StockDividend(String id,int year){
        this.stockId = id;
        this.reportYear = year;
    }

    /*public void setStockId(String stockId) {
        this.stockId = stockId;
    }
    public void setReportYear(int reportYear) {
        this.reportYear = reportYear;
    }*/

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public void setDividendDate(Date dividendDate) {
        this.dividendDate = dividendDate;
    }

    public String getStockId(){
        return this.stockId;
    }
    public String getStockName(){
        return this.stockName;
    }
    public int getReportYear(){
        return this.reportYear;
    }
    public Date getRegistDate(){
        return this.registDate;
    }
    public Date getDividendDate(){
        return this.dividendDate;
    }
}
