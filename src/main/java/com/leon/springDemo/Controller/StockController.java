package com.leon.springDemo.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.leon.springDemo.BusinessObject.RealStock;
import com.leon.springDemo.BusinessObject.StockListReader;
import com.leon.springDemo.BusinessObject.ThreadRunner;
import com.leon.springDemo.Entity.MyProxy;
import com.leon.springDemo.Entity.ProxyPkClass;
import com.leon.springDemo.Entity.Stock;
import com.leon.springDemo.Entity.StockDividend;
import com.leon.springDemo.Repository.MyProxyRepository;
import com.leon.springDemo.Repository.StockDividendRepository;
import com.leon.springDemo.Util.HttpHelper;
import com.leon.springDemo.Util.HttpHelperUsingProxy;
import com.leon.springDemo.Util.ProxyPool;
import com.leon.springDemo.Util.SpringContextUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StockController {
    @Autowired
    private StockDividendRepository stockDivRep;
    @Autowired
    private MyProxyRepository proxyRepository;
    private RealStock realStock;

    @RequestMapping("/lastStockDividend")
    public StockDividend getLastDividend() {
        return stockDivRep.getLastDividend() ;
    }

    @GetMapping(path = "/addDividend")
    public @ResponseBody
    String addStockDividend() {
        String root = RealStock.class.getResource("/").getPath();
        ArrayList stockCodes = StockListReader.ReadFile(root+"stockList.txt");
        int lastIndex =stockCodes.indexOf(stockDivRep.getLastDividend().getStockId());
        int i = 0;
        List<ThreadRunner> runners = new ArrayList<ThreadRunner>();
        while (i<10){
            runners.add(i, new ThreadRunner("runner" + i, stockCodes.size(), lastIndex+i, stockCodes));
            runners.get(i).start();
            i++;
        }
        return "started";
    }

    @GetMapping(path = "/allDividend")
    public @ResponseBody
    Iterable<StockDividend> getAllDividends() {
        // This returns a JSON or XML with the users
        return stockDivRep.findAll();
    }

    @GetMapping(path = "/addProxy")
    public @ResponseBody
    String addProxy() {

        return "inited";
    }

    @RequestMapping("/proxies")
    public List<MyProxy> getProxies(){
        ProxyPool pool = ProxyPool.getInstance();
        List<MyProxy> proxies = pool.getProxies();
        proxies.forEach(p->{
            proxyRepository.save(p);
        });
        return pool.getProxies();
    }

    @RequestMapping("/testnodata")
    public String testND(){
        String res = "";
        int lastIndex=0;
        String stockCode;
        ArrayList stockCodes = StockListReader.ReadFile("stockList.txt");
        lastIndex =stockCodes.indexOf(stockDivRep.getLastDividend().getStockId());
        List<StockDividend> sds = new ArrayList<StockDividend>();
        String successMsg = "";

        while (lastIndex < 3000) {
            stockCode = stockCodes.get(lastIndex).toString();
            if (sds != null) {
                sds.forEach(d -> {
                            stockDivRep.save(d);
                        }
                );
                lastIndex =stockCodes.indexOf(stockDivRep.getLastDividend().getStockId());
            }
            lastIndex++;
            successMsg = successMsg + stockCode + "saved !\n";
        }
        return  res;
    }

}
