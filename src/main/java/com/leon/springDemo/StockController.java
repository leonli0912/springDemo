package com.leon.springDemo;
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

}
