package com.mrgao.java.base.designpattern.decorator.inuputstream;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mr.Gao
 * @apiNote: 获取调用InputStream的read方法的次数
 * @date 2025/4/16 10:02
 */
public class CountExecReadInputStream extends InputStream {

    /**
     * 统计调用read方法的次数
     */
    private final AtomicInteger rc = new AtomicInteger(0);

    private final InputStream inputStream;

    public CountExecReadInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        rc.incrementAndGet();
        return inputStream.read();
    }

    /**
     * 获取调用read方法的次数
     *
     * @return
     */
    public int getRc() {
        return rc.get();
    }


    @Override
    public void close() throws IOException {
        super.close();
    }
}
