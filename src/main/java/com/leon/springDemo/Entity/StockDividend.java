package com.leon.springDemo.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;
import javax.persistence.Column;
@Entity
@IdClass(PkClass.class)
public class StockDividend {
    @Id
    @Column(columnDefinition="VARCHAR(8)")
    private String stockId;
    private String stockName;
    @Id
    private int reportYear;
    private Date registDate;
    private Date dividendDate;
    public StockDividend(){}
    /*public StockDividend(String id,int year){
        this.stockId = id;
        this.reportYear = year;
    }*/

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
    public void setReportYear(int reportYear) {
        this.reportYear = reportYear;
    }

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
