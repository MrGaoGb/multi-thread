package com.mrgao.java.base.iostream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/16 15:13
 */
public class TestInputStreamReaderWriter {

    public static void main(String[] args) throws Exception {

        String filePath = "D:\\workspace\\xbqx-workspace\\output.txt";

        // 读取文件
        //input(filePath);

        // 结合 BufferedReader 使用
        inputBufferedReader(filePath);
    }


    /**
     * 读取文件
     *
     * @param filePath
     * @throws IOException
     */
    private static void input(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis);) {
            int data;
            int count = 0;
            StringBuilder content = new StringBuilder();
            while ((data = isr.read()) != -1) {
                content.append((char) data);
                count++;
            }
            System.out.println("读取到的内容:" + content + ", 读取次数:" + count + "次!");
        }
    }

    /**
     * 结合 BufferedReader 使用
     *
     * @param filePath
     * @throws IOException
     */
    private static void inputBufferedReader(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);
        ) {

            String data;
            int count = 0;
            StringBuilder content = new StringBuilder();
            while ((data = br.readLine()) != null) {
                content.append(data);
                count++;
            }
            System.out.println("读取到的内容:" + content + ", 读取次数:" + count + "次!");
        }
    }
}
