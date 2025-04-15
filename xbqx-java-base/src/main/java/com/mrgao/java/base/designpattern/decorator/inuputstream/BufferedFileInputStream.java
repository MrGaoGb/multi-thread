package com.mrgao.java.base.designpattern.decorator.inuputstream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 实现缓冲区输入流
 * @Author Mr.Gao
 * @Date 2025/4/16 0:20
 */
public class BufferedFileInputStream extends InputStream {

    private final FileInputStream fileInputStream;

    public BufferedFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    /**
     * 定义一个缓冲区，默认大小为8192
     */
    private final byte[] buffer = new byte[8192];

    /**
     * 当前读取位置，默认为-1
     */
    private int position = -1;
    /**
     * 文件的实际容量
     */
    private int capacity = -1;

    @Override
    public int read() throws IOException {
        if (canReadBuffer()) {
            return readFromBuffer();
        }
        // 刷新缓冲区
        refreshBuffer();
        if (canReadBuffer()) {
            return readFromBuffer();
        }
        return -1;
    }

    private void refreshBuffer() throws IOException {
        // 实际读取的容量
        capacity = fileInputStream.read(buffer);
        System.out.println("刷新换冲区了, 当前大小为:" + capacity);
        if (capacity == -1) {
            position = -1;
        } else {
            // 重置读取位置
            position = 0;
        }
    }

    private int readFromBuffer() {
        return buffer[position++] & 0xff;
    }

    private boolean canReadBuffer() {
        if (capacity == -1) {
            // 表示容量为空
            return false;
        }
        if (position == capacity) {
            return false;
        }
        return true;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
