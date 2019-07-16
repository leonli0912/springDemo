package com.leon.springDemo;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.concurrent.atomic.AtomicLong;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @RequestMapping("/stock")
    public Stock stock(@RequestParam(value="id", defaultValue="sz00001") String id){

        return new Stock(id);
    }

    @RequestMapping("/stockDividend")
    public StockDividend dividendHistory(@RequestParam(value="id", defaultValue="sz00001") String id,
                                         @RequestParam(value="year", defaultValue="2018") int year){
        return  new StockDividend(id,year);
    }
    @RequestMapping("/dividends")
    public List<StockDividend> getDividends(int year){
        List<StockDividend> dividends = new ArrayList<>();
        StockDividend d1 = new StockDividend("sh000001",2018);
        d1.setStockName("stockname1");
        d1.setDividendDate(new Date());

        dividends.add(d1);
        StockDividend d2 = new StockDividend("sh000002",2018);
        d1.setStockName("stockname2");
        d1.setDividendDate(new Date());

        dividends.add(d1);
        return dividends;
    }

}
