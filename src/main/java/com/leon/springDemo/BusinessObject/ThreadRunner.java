package com.leon.springDemo.BusinessObject;

import com.leon.springDemo.Entity.StockDividend;
import com.leon.springDemo.Repository.StockDividendRepository;

import java.util.ArrayList;
import java.util.List;

public class ThreadRunner implements Runnable {
    private String stockCode;
    private int maxIndex;
    private ArrayList stockCodes;
    private volatile RealStock realStock;
    private StockDividendRepository stockDivRep;

    private String threadName;
    private Thread t;
    private volatile int lastIndex;

    public ThreadRunner(String name, int iMaxIndex, int iLastIndex, ArrayList codes) {
        this.threadName = name;
        this.maxIndex = iMaxIndex;
        this.lastIndex = iLastIndex;
        this.stockCodes = codes;
    }

    @Override
    public void run() {
        List<StockDividend> sds = new ArrayList<StockDividend>();
        String successMsg = "";
        //realStock = new RealStock();
        realStock = new RealStock();

        while(lastIndex<maxIndex){
            stockCode = stockCodes.get(lastIndex).toString();
            System.out.println("Thread: " + threadName+",stock:"+stockCode + ", " + lastIndex);

            sds = realStock.getHistoryDividend(stockCode, realStock.getHistoryDividendString(stockCode));
            if(sds != null){
                sds.forEach(d -> stockDivRep.save(d));
            }
            lastIndex++;
            successMsg = successMsg + stockCode + "saved !\n";
        }

    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

