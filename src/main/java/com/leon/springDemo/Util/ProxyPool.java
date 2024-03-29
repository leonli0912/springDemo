package com.leon.springDemo.Util;

import com.leon.springDemo.Entity.MyProxy;
import com.leon.springDemo.Repository.MyProxyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ProxyPool {
    private ArrayList<MyProxy> proxies;
    private MyProxyRepository proxyRepository;
    private static final ProxyPool instance = new ProxyPool();
    private ProxyPool(){
        proxyRepository=(MyProxyRepository)SpringContextUtil.getBean("myProxyRepository");
        proxies = new ArrayList<MyProxy>();
        prepareProxy();
    }
    public static ProxyPool getInstance(){
        return instance;
    }
    private void prepareProxy(){
        int page = 1;

        while(page<=5){
            try {
                String proxyHtml = new HttpHelperDirect("utf-8").doGet("https://www.xicidaili.com/nn/"+page);
                parseHtml(proxyHtml);
            }catch (Exception e){
                e.printStackTrace();
                System.out.print(e);
            }
            page++;
        }
    }

    private void parseHtml(String htmlText) {
        Document document = Jsoup.parse(htmlText);
        Elements allElements = document.select("#ip_list > tbody>tr") ;
        for (Element element :allElements){
            if (element.children().first().tag().toString().equals("th")){
                continue;
            }
            proxies.add(new MyProxy(element.child(1).text(),element.child(2).text())) ;
            System.out.println("proxy pool add "+ element.child(1).text()+':'+element.child(2).text());
        }
    }

    public ArrayList<MyProxy> getProxies(){
        return proxies;
    }

    public void save(MyProxy proxy) {
        proxyRepository.save(proxy);
    }

    public void remove(MyProxy proxy){
        if (proxies.contains(proxy)){
            proxies.remove(proxy);
        }
    }

    public MyProxy getRandom(){
        int index = (int) (Math.random() * proxies.size());
        return proxies.get(index);
    }

    public int getSize(){
        return proxies.size();
    }
}
