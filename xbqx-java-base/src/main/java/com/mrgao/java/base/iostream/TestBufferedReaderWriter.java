package com.mrgao.java.base.iostream;

import java.io.*;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/16 14:41
 */
public class TestBufferedReaderWriter {
    public static void main(String[] args) throws IOException {

        // 文件路径
        String filePath = "D:\\workspace\\xbqx-workspace\\output.txt";

        // 读取文件内容
        read(filePath);

        // 向文件中写入内容
        //write(filePath);

    }

    /**
     * 读取文件内容
     *
     * @param filePath
     * @throws IOException
     */
    private static void read(String filePath) throws IOException {
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr);
        ) {
            String line;
            StringBuilder contentRead = new StringBuilder();
            int count = 0;
            while ((line = br.readLine()) != null) {
                contentRead.append(line);
                count++;
            }
            System.out.println("读取到的内容:" + contentRead + " ,总共读取了" + count + "次");
        }
    }


    /**
     * 写入文件
     *
     * @param filePath
     * @throws IOException
     */
    private static void write(String filePath) throws IOException {
        String contentToWrite = "Hello, BufferedWriter!\nThis is a test.";
        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw);
        ) {
            bw.write(contentToWrite);
            bw.newLine(); // 写入换行符
            bw.write("Another line of text.");
        }
    }
}
