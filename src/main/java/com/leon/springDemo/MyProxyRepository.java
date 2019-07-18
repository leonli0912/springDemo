package com.leon.springDemo;

import org.springframework.data.repository.CrudRepository;

public interface MyProxyRepository extends CrudRepository<MyProxy, ProxyPkClass> {
}
