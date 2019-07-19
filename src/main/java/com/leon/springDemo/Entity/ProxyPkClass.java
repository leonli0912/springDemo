package com.leon.springDemo.Entity;

import java.io.Serializable;

public class ProxyPkClass implements Serializable {
    private String proxyHost;
    private Integer proxyPort;

    public ProxyPkClass(){}

    public ProxyPkClass(String host, int port) {
        this.proxyHost = host;
        this.proxyPort = port;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((proxyHost == null) ? 0 : proxyHost.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
