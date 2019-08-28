package com.leon.springDemo.Repository;
import com.leon.springDemo.Entity.PkClass;
import com.leon.springDemo.Entity.StockDividend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StockDividendRepository extends CrudRepository<StockDividend, PkClass> {

    @Query(value = "SELECT * FROM stockdividend  where stockId like 'sh%' ORDER BY stockId DESC LIMIT 1",nativeQuery = true)
    StockDividend getLastDividend();

    @Query(value = "SELECT * FROM stockdividend WHERE reportYear = ?1 ORDER BY ratio DESC LIMIT 20",nativeQuery = true)
    Iterable<StockDividend> filter(String year);
}
