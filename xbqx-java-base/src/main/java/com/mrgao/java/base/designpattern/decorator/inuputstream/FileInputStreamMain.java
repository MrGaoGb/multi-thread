package com.mrgao.java.base.designpattern.decorator.inuputstream;

import cn.hutool.core.date.StopWatch;

import java.io.*;

/**
 * @Description 通过FileInputStream读取文件
 * @Author Mr.Gao
 * @Date 2025/4/16 0:11
 */
public class FileInputStreamMain {

    /**
     * TODO(明天实现) 下一个问题：你可以写一个装饰器，记录当前inputStream调用过多少次read方法吗?
     *
     * @param args
     */
    public static void main(String[] args) {

        // 创建一个文件输入流对象
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("开始读取文件...");
        try (
                // 从缓冲区读取
                InputStream bfis = new BufferedFileInputStream(new FileInputStream(new File("D:\\技术文档\\JAVA.pdf")));
//                FileInputStream fis = new FileInputStream(new File("D:\\技术文档\\JAVA.pdf"))
        ) {
            while (true) {
//                int read = fis.read();
                int bufferRead = bfis.read();

//                if (read != bufferRead) {
//                    throw new RuntimeException("文件数据读取错误!");
//                }

                if (bufferRead == -1) {
                    // -1表示文件读取完毕
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // 文件找不到异常
            throw new RuntimeException(e);
        } catch (IOException e) {
            // IO异常
            throw new RuntimeException(e);
        } finally {
            stopWatch.stop();
            System.out.println("文件读取完毕，耗时：" + stopWatch.getTotalTimeMillis() + "ms");
        }
    }

}
