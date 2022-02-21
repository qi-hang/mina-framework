package com.test.mina.codec.reflect.serializer;


import com.test.utils.ByteBuffUtil;
import org.apache.mina.core.buffer.IoBuffer;

public class ByteSerializer extends Serializer {

    @Override
    public Byte decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        return ByteBuffUtil.readByte(in);
    }

    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {
        System.out.println("bb");
        if (value instanceof Integer){
            System.out.println("1");
            ByteBuffUtil.writeByte(out, ((Integer) value).byteValue());
        }else if (value instanceof Short){
            System.out.println("2");
            ByteBuffUtil.writeByte(out, ((Short) value).byteValue());
        }else if (value instanceof Long){
            System.out.println("3");
            ByteBuffUtil.writeByte(out, ((Long) value).byteValue());
        }else {
            System.out.println("4");
            ByteBuffUtil.writeByte(out, (byte) value);
        }
    }

}
