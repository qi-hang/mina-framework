package com.test.mina.codec.reflect.serializer;

import com.test.utils.ByteBuffUtil;
import org.apache.mina.core.buffer.IoBuffer;

public class IntSerializer extends Serializer {

    @Override
    public Integer decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        return ByteBuffUtil.readInt(in);
    }

    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {
        if (value instanceof Short) {
            ByteBuffUtil.writeInt(out, ((Short) value).intValue());
        } else if (value instanceof Long) {
            ByteBuffUtil.writeInt(out, ((Long) value).intValue());
        } else if (value instanceof Byte) {
            ByteBuffUtil.writeInt(out, ((Byte) value).intValue());
        } else {
            ByteBuffUtil.writeInt(out, (int) value);
        }
    }
    public void encodeUnsignedShort(IoBuffer out, Object value, Class<?> wrapper) {
        if (value instanceof Short) {
            ByteBuffUtil.writeUnsignedShort(out, ((Short) value).intValue());
        } else if (value instanceof Long) {
            ByteBuffUtil.writeUnsignedShort(out, ((Long) value).intValue());
        } else if (value instanceof Byte) {
            ByteBuffUtil.writeUnsignedShort(out, ((Byte) value).intValue());
        } else {
            ByteBuffUtil.writeUnsignedShort(out, (int) value);
        }
    }
}
