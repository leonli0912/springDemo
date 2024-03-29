package com.leon.springDemo.BusinessObject;

import java.io.*;
import java.util.ArrayList;

public class StockListReader {
    public static ArrayList ReadFile(String filePath) {
        System.out.println("start read file...");
        ArrayList aStockCodes = new ArrayList<String>();
        InputStream is=StockListReader.class.getResourceAsStream(filePath);
        try (
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is)) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            int lineIndex = 0;
            while ((line = bufferedReader.readLine()) != null) {
                // 一次读入一行数据
                String[] aStockList = line.split(",");
                System.out.println(aStockList.length);
                for (String stock:aStockList
                ) {
                    String[] stockCode = stock.split("\\.");
                    aStockCodes.add(stockCode[1].toLowerCase()+stockCode[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aStockCodes;
    }

    public static void writeFile() {
        try {
            File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write("我会写入文件啦1\r\n"); // \r\n即为换行
                out.write("我会写入文件啦2\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
