package com.leon.springDemo.Util;

public interface HttpHelper {

    public String doGet(String url) throws Exception;

    //public URLConnection openConnection(URL localURL) throws IOException;

    public void setCharset(String scharset);

}
