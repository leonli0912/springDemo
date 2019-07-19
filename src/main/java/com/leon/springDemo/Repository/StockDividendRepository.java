package com.leon.springDemo.Repository;
import com.leon.springDemo.Entity.PkClass;
import com.leon.springDemo.Entity.StockDividend;
import org.springframework.data.repository.CrudRepository;
public interface StockDividendRepository extends CrudRepository<StockDividend, PkClass> {

}
