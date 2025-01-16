package com.mrgao.java.base.iostream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/16 10:48
 */
public class TestByteArrayInOutputStream {

    public static void main(String[] args) throws IOException {

        // 读取字节数组
        //readContent();

        // 写入字节数组
        writeContent();
    }

    /**
     * 读取字节数组
     *
     * @throws IOException
     */
    private static void readContent() throws IOException {
        byte[] bytes = "Hello, World!".getBytes();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = bais.read()) != -1) {
                content.append((char) data);
            }

            System.out.println("读取到的内容:" + content);
        }
    }

    /**
     * 写入字节数组
     *
     * @throws IOException
     */
    private static void writeContent() throws IOException {
        byte[] bytes = "Hello, World!".getBytes();
        System.out.println("准备写入字节数组, 长度:" + bytes.length);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(bytes); // 将字节数组写入到输出流中

            System.out.println("字节数组已写入输出流中!");
            // 获取字节数组
            byte[] outputBytes = baos.toByteArray();
            System.out.println("从输出流中获取字节数组，长度:" + outputBytes.length);
            // 输出字节数组内容
            readContent(outputBytes);
        }
    }


    /**
     * 读取字节数组
     *
     * @param bytes
     * @throws IOException
     */
    private static void readContent(byte[] bytes) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = bais.read()) != -1) {
                content.append((char) data);
            }

            System.out.println("读取到的内容:" + content);
        }
    }
}
