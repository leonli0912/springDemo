package com.leon.springDemo.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.leon.springDemo.BusinessObject.RealStock;
import com.leon.springDemo.BusinessObject.StockListReader;
import com.leon.springDemo.Entity.MyProxy;
import com.leon.springDemo.Entity.ProxyPkClass;
import com.leon.springDemo.Entity.Stock;
import com.leon.springDemo.Entity.StockDividend;
import com.leon.springDemo.Repository.MyProxyRepository;
import com.leon.springDemo.Repository.StockDividendRepository;
import com.leon.springDemo.Util.ProxyPool;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StockController {
    @Autowired
    private StockDividendRepository stockDivRep;
    @Autowired
    private MyProxyRepository proxyRepository;
    private RealStock realStock;

    @RequestMapping("/stock")
    public Stock stock(@RequestParam(value = "id", defaultValue = "sz00001") String id) {

        return new Stock(id);
    }

    @RequestMapping("/stockDividend")
    public StockDividend dividendHistory(@RequestParam(value = "id", defaultValue = "sz00001") String id,
                                         @RequestParam(value = "year", defaultValue = "2018") int year) {
        return new StockDividend();
    }

    @GetMapping(path = "/addDividend")
    public @ResponseBody
    String addStockDividend() {
        realStock = new RealStock();
        String root = realStock.getClass().getResource("/").getPath();
        ArrayList stockCodes = StockListReader.ReadFile(root+"stockList.txt");
        for(int i=0;i<stockCodes.size();i++){
            String stockCode = stockCodes.get(i).toString();
            System.out.println("number:..."+i+" stock code: "+stockCode);
            try{
                realStock.getHistoryDividendString(stockCode);
            }catch (Exception e){

            }
        }
        StockDividend sd = new StockDividend();
        sd.setStockId("test01");
        sd.setReportYear(2019);
        sd.setStockName("for testing");
        stockDivRep.save(sd);
        return "saved!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<StockDividend> getAllDividends() {
        // This returns a JSON or XML with the users
        return stockDivRep.findAll();
    }

    @RequestMapping("/dividends")
    public List<StockDividend> getDividends(int year) {
        List<StockDividend> dividends = new ArrayList<>();
        StockDividend d1 = new StockDividend();
        d1.setStockName("stockname1");
        d1.setDividendDate(new Date());

        dividends.add(d1);
        StockDividend d2 = new StockDividend();
        d1.setStockName("stockname2");
        d1.setDividendDate(new Date());

        dividends.add(d1);
        return dividends;
    }

    @GetMapping(path = "/addProxy")
    public @ResponseBody
    String addProxy() {

        return "inited";
    }

    @RequestMapping("/proxies")
    public List<MyProxy> getProxies(){
        ProxyPool pool = new ProxyPool();
        List<MyProxy> proxies = pool.getProxies();
        proxies.forEach(p->{
            proxyRepository.save(p);
        });
        return pool.getProxies();
    }

}
