package com.test.mina.codec.reflect.serializer;


import com.test.utils.ByteBuffUtil;
import com.test.utils.ReflectUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;

/**
 * 数组属性序列化
 * 注：由于数组元素bean没有像Message一样注册id，
 * 因此数组的元素不能是父类或抽象类
 */
public class ArraySerializer extends Serializer {
    @Override
    public Object decode(IoBuffer in, Class<?> type, Class<?> wrapper) {
        System.out.println("decodingbyte-2length");
        int size = ByteBuffUtil.readShort(in);
        System.out.println("size::"+size+"##"+type);
        Object array = ReflectUtil.newArray(type, wrapper, size);

        for (int i = 0; i < size; i++) {
            Serializer fieldCodec = getSerializer(wrapper);
            Object eleValue = fieldCodec.decode(in, wrapper, null);
            Array.set(array, i, eleValue);
        }

        return array;
    }

    public static Object decodevalue(int value, IoBuffer in, Class<?> type, Class<?> wrapper) {
        //System.out.println("decodingbyte-1length");
        int size=0;
        if(value==1){
             size = ByteBuffUtil.readByte(in);
        }else if(value==3){
             size = ByteBuffUtil.readInt(in);
        }else{
            System.out.println("length missed in ArraySerializer ");
        }
        //System.out.println("size::"+size+"##"+type);
        Object array = ReflectUtil.newArray(type, wrapper, size);

        for (int i = 0; i < size; i++) {
            Serializer fieldCodec = getSerializer(wrapper);
            Object eleValue = fieldCodec.decode(in, wrapper, null);
            Array.set(array, i, eleValue);
        }

        return array;
    }

    @Override
    public void encode(IoBuffer out, Object value, Class<?> wrapper) {
        if (value == null) {
            ByteBuffUtil.writeShort(out, (short) 0);
            return;
        }
        int size = Array.getLength(value);
        System.out.println("length:"+size);
        ByteBuffUtil.writeShort(out, (short) size);
        encodeObject(out, value, wrapper, size);
    }

    public void encode(IoBuffer out, Object value, Class<?> wrapper, int type) {
        if (value == null) {
            if (type == 1) {
                ByteBuffUtil.writeByte(out, (byte) 0);
            } else if (type == 2) {
            } else if (type == 3) {
                ByteBuffUtil.writeInt(out, 0);
            } else {
                ByteBuffUtil.writeShort(out, (short) 0);
            }
            return;
        }
        int size = Array.getLength(value);
        if (type == 1) {
            ByteBuffUtil.writeByte(out, (byte) size);
        } else if (type == 2) {
        } else if (type == 3) {
            ByteBuffUtil.writeInt(out, size);
        } else {
            ByteBuffUtil.writeShort(out, (short) size);
        }
        encodeObject(out, value, wrapper, size);
    }

    private void encodeObject(IoBuffer out, Object value, Class<?> wrapper, int size) {
        for (int i = 0; i < size; i++) {
            Object elem = Array.get(value, i);
            Class<?> clazz = elem.getClass();
            Serializer fieldCodec = getSerializer(clazz);
            fieldCodec.encode(out, elem, wrapper);
        }
    }

}
