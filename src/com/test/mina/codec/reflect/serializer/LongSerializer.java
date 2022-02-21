package com.test.mina.codec.reflect.serializer;


import com.test.utils.ByteBuffUtil;
import org.apache.mina.core.buffer.IoBuffer;

public class LongSerializer extends Serializer {

    @Override
    public Long decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        System.out.println("errorrr");
        return ByteBuffUtil.readLong(in);
    }

    public Long uintdecode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        System.out.println("errorrr");
        return ByteBuffUtil.readUnsignedInt(in);
    }

    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {

       // ByteBuffUtil.writeBoolean(out,true);
        ByteBuffUtil.writeLong(out, (long) value);
    }
    public void encodeUnsignedInt(IoBuffer out, Object value, Class<?> wrapper) {
        ByteBuffUtil.writeUnsignedInt(out,(long) value);
    }
}
