package com.leon.springDemo.BusinessObject;

import com.leon.springDemo.Entity.StockDividend;
import com.leon.springDemo.Repository.StockDividendRepository;
import com.leon.springDemo.Util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ThreadRunner implements Runnable {
    private String stockCode;
    private int maxIndex;
    private ArrayList stockCodes;
    private volatile RealStock realStock;
    @Autowired
    private volatile StockDividendRepository stockDivRep;

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
        stockDivRep = (StockDividendRepository)SpringContextUtil.getBean(StockDividendRepository.class);
        List<StockDividend> sds = new ArrayList<StockDividend>();
        String successMsg = "";
        //realStock = new RealStock();
        realStock = new RealStock();

        while (lastIndex < maxIndex) {
            stockCode = stockCodes.get(lastIndex).toString();
            System.out.println("Thread: " + threadName + ",stock:" + stockCode + ", " + lastIndex);

            sds = realStock.getHistoryDividend(stockCode, realStock.getHistoryDividendString(stockCode));
            if (sds != null) {
                sds.forEach(d -> {
                            //System.out.println(d);
                            stockDivRep.save(d);
                        }
                );
                lastIndex =stockCodes.indexOf(stockDivRep.getLastDividend().getStockId());
            }

            System.out.println("Thread: " + threadName + ",stock:" + stockCode + ", " + lastIndex+"saved!");
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

