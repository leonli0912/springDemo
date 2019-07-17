package com.leon.springDemo;

import java.io.Serializable;

public class PkClass implements Serializable {
    private String stockId;
    private int reportYear;

    public PkClass(String stockId, int reportYear) {
        this.stockId = stockId;
        this.reportYear = reportYear;
    }
}
