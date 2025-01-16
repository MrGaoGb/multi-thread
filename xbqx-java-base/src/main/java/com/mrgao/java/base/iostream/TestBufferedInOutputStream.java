package com.mrgao.java.base.iostream;

import java.io.*;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/16 11:07
 */
public class TestBufferedInOutputStream {
    public static void main(String[] args) throws IOException {
        // 读取文件路径
        String filePath = "D:\\workspace\\xbqx-workspace\\output.txt";

        // 从目标文件读取内容
        //readFile(filePath);

        // 写入文件
        writeFile(filePath);
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @throws IOException
     */
    private static void readFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = bis.read()) != -1) {
                content.append((char) data);
            }
            System.out.println("读取到的内容:" + content);
        }
    }


    /**
     * 写入文件
     *
     * @param filePath
     */
    private static void writeFile(String filePath) throws IOException {
        String contentToWrite = "Hello, BufferedOutputStream!";

        try (FileOutputStream fos = new FileOutputStream(filePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(contentToWrite.getBytes());
            bos.flush(); // 确保所有数据都被写入文件中
        }

        System.out.println("文件写入成功!");
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = bis.read()) != -1) {
                content.append((char) data);
            }

            System.out.println("读取到的内容:" + content);
            boolean compareContent = content.toString().equals(contentToWrite);
            System.out.println("内容是否相同:" + compareContent);
        }
    }
}
