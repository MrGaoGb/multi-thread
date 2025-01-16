package com.mrgao.java.base.iostream;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/16 15:42
 */
public class TestOutputStreamWriter {

    public static void main(String[] args) throws IOException {

        String filePath = "D:\\workspace\\xbqx-workspace\\output.txt";

        // 将字符串写入文件
        //output(filePath);

        // 使用 BufferedWriter 写入文件
        outputWithBuffered(filePath);
    }

    /**
     * 写入文件
     *
     * @param filePath
     * @throws IOException
     */
    private static void output(String filePath) throws IOException {
        String contentToWrite = "Hello, OutputStreamWriter!";
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(contentToWrite);
            osw.flush();
        }
    }

    /**
     * 结合 BufferedWriter 使用
     *
     * @param filePath
     * @throws IOException
     */
    private static void outputWithBuffered(String filePath) throws IOException {
        String contentToWrite = "Hello, BufferedWriter!\nThis is a test.";
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {

            bw.write(contentToWrite);

            bw.newLine(); // 写入换行符
            bw.write("Another line of text.");
        }
    }
}
