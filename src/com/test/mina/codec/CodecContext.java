package com.test.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;


public class CodecContext {

    /**
     * last interrupted　buff
     */
    private IoBuffer buffer;

    public CodecContext() {
        buffer = IoBuffer.allocate(32).setAutoExpand(true).setAutoShrink(true);
    }

    public IoBuffer append(IoBuffer in) {
        this.buffer.put(in);
        return this.buffer;
    }

    public IoBuffer getBuffer() {
        return this.buffer;
    }
}
