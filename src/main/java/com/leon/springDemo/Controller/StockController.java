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
        ThreadRunner runner1 = new ThreadRunner("runner1",stockCodes.size(),lastIndex,stockCodes);
        runner1.start();

        ThreadRunner runner2 = new ThreadRunner("runner2",stockCodes.size(),lastIndex+1,stockCodes);
        runner2.start();

        /*List<StockDividend> sds = new ArrayList<StockDividend>();
        String successMsg = "";
        realStock = new RealStock();
        String root = realStock.getClass().getResource("/").getPath();
        ArrayList stockCodes = StockListReader.ReadFile(root+"stockList.txt");
        int lastIndex =stockCodes.indexOf(stockDivRep.getLastDividend().getStockId());
        //stockDivRep.getLastDividend().getStockId()
        for(int i=lastIndex+1;i<stockCodes.size();i++){//stockCodes.size()
            String stockCode = stockCodes.get(i).toString();//stockCodes.get(i).toString();
            System.out.println("number:..."+i+" stock code: "+stockCode);
            try{
                sds = realStock.getHistoryDividend(stockCode,realStock.getHistoryDividendString(stockCode)) ;
            }catch (Exception e){
                e.printStackTrace();
            }
            sds.forEach(d->stockDivRep.save(d));
            successMsg = successMsg + stockCode + "saved !\n";
        }
        return successMsg;*/
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

    @RequestMapping("/testproxy")
    public String testProxy(){
        String res = "";
        HttpHelper hp = new HttpHelperUsingProxy("GBK", true);
        ((HttpHelperUsingProxy) hp).setProxy("112.85.166.241",9999);
        try {
            res = hp.doGet("https://www.baidu.com/");
            ((HttpHelperUsingProxy) hp).saveProxy();
        } catch (java.lang.Exception e){
            res = "get failed";
        }
        return  res;

    }

}
