package com.leon.springDemo.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class HttpHelperDirect implements HttpHelper {
    private String charset;

    {
        charset = "GBK";
    }

    /**
     * constructor
     *
     * @param scharset
     */
    public HttpHelperDirect(String scharset) {
        charset = scharset;
    }

    public void setCharset(String scharset) {
        charset = scharset;
    }

    public String doGet(String url) throws Exception {

        URL localURL = new URL(url);//new URL(null, url, new sun.net.www.protocol.https.Handler());
        URLConnection connection = this.openConnection(localURL);
        //Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        //httpURLConnection.setRequestProperty("Accept-Charset", charset);
        //httpURLConnection.setRequestProperty("Content-Type", "application/javascript");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        String inputCharset;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine;
        //响应失败
        try {
            if (httpURLConnection.getResponseCode() >= 300) {
                System.out.println("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
                resultBuffer = new StringBuffer(doGet(url));
            }

            inputStream = httpURLConnection.getInputStream();
            if (charset == "GBK") {
                inputCharset = "GB2312";
            } else {
                inputCharset = charset;
            }
            inputStreamReader = new InputStreamReader(inputStream, inputCharset);//GB2312 for get historyPrice
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } catch (javax.net.ssl.SSLHandshakeException e) {
            System.out.println("SSH handshake error");

        } catch (ConnectException e) {
            System.out.println("connection time out");
            resultBuffer = new StringBuffer(doGet(url));
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }

    private URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;

        connection = localURL.openConnection();

        return connection;
    }

}
