package com.test.mina.codec.reflect.serializer;

import com.test.utils.ByteBuffUtil;
import org.apache.mina.core.buffer.IoBuffer;

public class DoubleSerializer extends Serializer {

    @Override
    public Double decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        return ByteBuffUtil.readDouble(in);
    }

    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {
        ByteBuffUtil.writeDouble(out, (double) value);
    }

}
