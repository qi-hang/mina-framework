package com.test.mina.codec.reflect.serializer;

import com.test.utils.ByteBuffUtil;
import org.apache.mina.core.buffer.IoBuffer;

public class StringSerializer extends Serializer {

    /**默认byte长度String*/
    @Override
    public String decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        return ByteBuffUtil.readByteString(in);
    }

    /**short长度String*/
    public String decodeShort(IoBuffer in) {
        return ByteBuffUtil.readShortString(in);
    }

    /**short长度String*/
    public void encodeShort(IoBuffer out, String msg) {
        ByteBuffUtil.writeShortString(out, msg);
    }


    /**默认byte长度String*/
    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {
        ByteBuffUtil.writeByteString(out, (String) value);
    }

}
