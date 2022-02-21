package com.test.mina.codec.reflect.serializer;

import com.test.utils.ByteBuffUtil;
import org.apache.mina.core.buffer.IoBuffer;

public class FloatSerializer extends Serializer {

    @Override
    public Float decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        return ByteBuffUtil.readFloat(in);
    }

    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {
        ByteBuffUtil.writeFloat(out, (float) value);
    }

}
