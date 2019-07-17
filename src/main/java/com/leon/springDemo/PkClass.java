package com.leon.springDemo;

import java.io.Serializable;

public class PkClass implements Serializable {
    private String stockId;
    private int reportYear;

    public String getStockId() {
        return stockId;
    }

    public int getReportYear() {
        return reportYear;
    }
    public PkClass(String stockId, int reportYear) {
        this.stockId = stockId;
        this.reportYear = reportYear;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((stockId == null) ? 0 : stockId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
