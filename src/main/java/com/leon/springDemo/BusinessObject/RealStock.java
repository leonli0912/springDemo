package com.leon.springDemo.BusinessObject;

import com.leon.springDemo.Entity.StockDividend;
import com.leon.springDemo.Util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RealStock {
    private HttpHelper urlHelper;
    private MySqlHelper mysql;

    public RealStock() {
        urlHelper = new HttpHelperUsingProxy("GBK", true);
    }

    private void initSqlHelper() {
        mysql = new MySqlHelper(DBConfiguration.url, DBConfiguration.userName, DBConfiguration.password);
    }

    public String getStockHistory(String stockCode) throws Exception {
        return getStockHistory(stockCode, 30);
    }

    public String getStockHistory(String stockCode, int interval) throws Exception {

        String s = null;
        s = urlHelper.doGet("https://finance.sina.com.cn/realstock/newcompany/" + stockCode + "/pqfq.js?_=14");
        String result = s.substring(0, s.indexOf("/*"));
        result = result.substring(s.indexOf("data:") + 6, result.length() - 3);
        //System.out.print("getStockHistory:" + result + "\n");
        return result;

    }

    public String getLatestPrice(String stockCode) throws Exception {
        String s = null;
        System.out.println("getLatestPrice:" + stockCode + "\n");
        s = urlHelper.doGet("http://hq.sinajs.cn/list=" + stockCode);
        String result = s.substring(s.indexOf("=") + 1).replace("\"", "");
        System.out.print(result + "\n");
        return result;
    }

    public String[] getHistoryDividendString(String stockCode) {
        String[] result;
        String rawHtml = "";
        try {
            rawHtml = getHistoryDividendRaw(stockCode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Get History Dividend Failed");
            return null;
        }
        result = ParseHtml(rawHtml);
        return result;
    }

    public List<StockDividend> getHistoryDividend(String stockCode, String[] strings) {
        List<StockDividend> sds = new ArrayList<StockDividend>();
        if (strings == null ||strings.length == 0) {
            return null;
        }
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                System.out.println(strings[i]);
                StockDividend sd = new StockDividend();
                sd.setStockId(stockCode);
                //2018年报：10派1.45元， 分红日 2019年06月26日 ，该批次分红当天的股息率 1.08% 。
                String[] substring1 = strings[i].trim().split("：");
                //get year,':' 2018年报
                int ryear = Integer.parseInt(substring1[0].substring(0, 4));
                sd.setReportYear(ryear);
                //get dividend,dividend date,ratio string:',' 10派1.45元，分红日 2019年06月26日，该批次分红当天的股息率 1.08%
                String[] substring2 = substring1[1].split("，");
                sd.setDivdendContent(substring2[0].trim());
                Date newDate;
                try {
                    newDate = new SimpleDateFormat("yyyy年MM月dd日").parse(substring2[1].substring(5, 16).trim());
                    sd.setDividendDate(newDate);
                } catch (java.lang.StringIndexOutOfBoundsException e){
                    //暂无公告分红日
                    try {
                        sd.setDividendDate(new SimpleDateFormat("yyyy年MM月dd日").parse("2019年12月31日"));
                    }catch (java.text.ParseException ee){}

                }catch (java.text.ParseException e) {
                    //
                }
                if (substring2.length>2){
                    sd.setRatio(Double.valueOf(substring2[2].split(" ")[1].split("%")[0]));
                }
                sds.add(sd);
                //    String sqlStatemement = sqlSharedStateMement + divId + "," + "'"+dividends[i].trim() +"'"+ ")";
                //    mysql.execute(sqlStatemement);
            }
        }
        return sds;
    }

    /*public void updateHistoryDividend(String stockCode, String[] dividends) throws java.sql.SQLException {
        final String sqlSharedStateMement = "insert into myschema.stockdividend(id,dividendId,dividendDetail)" +
                "value('" + stockCode + "',";
        final String querySql = "select * from myschema.stockdividend where id ='" + stockCode +
                "' and dividendId='";
        initSqlHelper();
        int divId = 0;
        if (dividends == null) {
            return;
        }
        for (int i = 0; i < dividends.length; i++) {
            if (dividends[i] != null) {
                if (mysql.executeQuery(querySql + divId + "'") == 0) {
                    String sqlStatemement = sqlSharedStateMement + divId + "," + "'" + dividends[i].trim() + "'" + ")";
                    mysql.execute(sqlStatemement);
                }
                divId++;
            }
        }
        destroySqlHelper();
    }*/

    private void destroySqlHelper() {
        mysql.destory();
    }

    private String[] ParseHtml(String htmlText) {
        String[] result = new String[100];
        Document document = Jsoup.parse(htmlText);
        Elements allElements = document.getElementsByClass("mt-1");
        for (Element element : allElements) {
            if (element.tag().toString().equals("ul")) {
                result = ParseText(element.text());
            }
        }
        return result;
    }

    private String[] ParseText(String longText) {
        if (longText.equals("")) {
            return null;
        }
        String[] dividends = longText.split("。");
        return dividends;
    }

    private String getHistoryDividendRaw(String stockCode) throws Exception {
        final String BASEURL = "https://androidinvest.com/Stock/HistoryDividend/";
        String requestUrl = BASEURL + stockCode;
        String response = null;
        urlHelper.setCharset("UTF-8");
        response = urlHelper.doGet(requestUrl);
        System.out.print("getStockHistory:" + stockCode + "\n");
        return response;
    }

}
