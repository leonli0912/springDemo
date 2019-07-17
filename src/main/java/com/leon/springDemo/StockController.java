package com.leon.springDemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StockController {
    @Autowired
    private StockDividendRepository stockDivRep;

    @RequestMapping("/stock")
    public Stock stock(@RequestParam(value = "id", defaultValue = "sz00001") String id) {

        return new Stock(id);
    }

    @RequestMapping("/stockDividend")
    public StockDividend dividendHistory(@RequestParam(value = "id", defaultValue = "sz00001") String id,
                                         @RequestParam(value = "year", defaultValue = "2018") int year) {
        return new StockDividend();
    }

    @GetMapping(path = "/add")
    public @ResponseBody
    String addStockDividend() {
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

}
