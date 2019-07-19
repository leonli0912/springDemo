package com.leon.springDemo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ProxyPkClass.class)
public class MyProxy {
    @Id
    @Column(columnDefinition="VARCHAR(16)")
    private String proxyHost;
    @Id
    private Integer proxyPort;
    private Boolean active;

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public Boolean getActive() {
        return active;
    }

    public MyProxy(){}

    public MyProxy(String host, String port){
        proxyHost=host;
        try {
            proxyPort = Integer.parseInt(port);
        }catch (NumberFormatException e){
            System.out.print(e);
        }
    }
}
