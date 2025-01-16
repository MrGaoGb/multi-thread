package com.mrgao.java.base.iostream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/16 10:14
 */
public class TestFileInOutputStream {

    public static void main(String[] args) throws IOException {
        // 读取文件路径
        String filePath = "D:\\workspace\\xbqx-workspace\\output.txt";

        // 向目标文件写入内容
        //writeFile(filePath);

        // 从目标文件读取内容
        readFile(filePath);
    }


    /**
     * 读取文件
     *
     * @param filePath
     * @throws IOException
     */
    private static void readFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = fis.read()) != -1) {
                content.append((char) data);
            }

            System.out.println("读取到的内容:" + content);
        }
    }

    /**
     * 写入文件
     *
     * @param filePath
     * @throws IOException
     */
    private static void writeFile(String filePath) throws IOException {

        // --向文件写入内容
        String contentToWrite = "Hello, World!";

        // --写入文件
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(contentToWrite.getBytes());
        }
    }
}
