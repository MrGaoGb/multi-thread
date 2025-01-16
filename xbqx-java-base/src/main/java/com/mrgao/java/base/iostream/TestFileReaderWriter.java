package com.mrgao.java.base.iostream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/16 14:06
 */
public class TestFileReaderWriter {

    public static void main(String[] args) throws IOException {
        // 读取文件路径
        String filePath = "D:\\workspace\\xbqx-workspace\\output.txt";

        // 测试 FileReadWrite
        testFileReadWrite(filePath);

    }

    private static void testFileReadWrite(String filePath) throws IOException {
        // 写入内容
        String contentToWrite = "Hello, FileWriter and FileReader!";

        // 使用 FileWriter 写入文件
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(contentToWrite);
        }

        // 使用 FileReader 读取文件
        StringBuilder contentRead = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath)) {
            int data;
            while ((data = fileReader.read()) != -1) {
                contentRead.append((char) data);
            }
        }

        System.out.println("读取到的内容:" + contentRead);

    }

}
