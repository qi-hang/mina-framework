package com.test.mina.codec.reflect.serializer;

import com.test.utils.ByteBuffUtil;
import org.apache.mina.core.buffer.IoBuffer;

public class ShortSerializer extends Serializer {

    @Override
    public Short decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        return ByteBuffUtil.readShort(in);
    }

    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {
        if (value instanceof Long) {
            ByteBuffUtil.writeShort(out, ((Long) value).shortValue());
        } else if (value instanceof Integer) {
            ByteBuffUtil.writeShort(out, ((Integer) value).shortValue());
        } else if (value instanceof Byte) {
            ByteBuffUtil.writeShort(out, ((Byte) value).shortValue());
        } else {
            ByteBuffUtil.writeShort(out, (short) value);
        }
    }

    public void encodeUnsignedByte(IoBuffer out, Object value, Class<?> wrapper) {
        if (value instanceof Long) {
            ByteBuffUtil.writeUnsignedByte(out, ((Long) value).shortValue());
        } else if (value instanceof Integer) {
            ByteBuffUtil.writeUnsignedByte(out, ((Integer) value).shortValue());
        } else if (value instanceof Byte) {
            value = ((Byte) value).shortValue();
            ByteBuffUtil.writeUnsignedByte(out, ((Byte) value).shortValue());
        } else {
            ByteBuffUtil.writeUnsignedByte(out, (short) value);
        }
    }

}
