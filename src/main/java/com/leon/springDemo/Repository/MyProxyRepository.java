package com.leon.springDemo.Repository;

import com.leon.springDemo.Entity.MyProxy;
import com.leon.springDemo.Entity.ProxyPkClass;
import org.springframework.data.repository.CrudRepository;

public interface MyProxyRepository extends CrudRepository<MyProxy, ProxyPkClass> {
}
