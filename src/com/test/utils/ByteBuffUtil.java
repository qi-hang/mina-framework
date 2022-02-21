package com.test.utils;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public final class ByteBuffUtil {

    private static final Logger logger = LoggerFactory.getLogger(ByteBuffUtil.class);

    public static boolean readBoolean(IoBuffer buf) {
        return buf.get() > 0;
    }

    public static void writeBoolean(IoBuffer buf, boolean value) {
        buf.put(value ? (byte) 1 : (byte) 0);
    }

    public static byte readByte(IoBuffer buf) {
        return buf.get();
    }

    public static void writeByte(IoBuffer buf, byte value) {
        buf.put(value);
    }

    public static char readChar(IoBuffer buf) {
        return buf.getChar();
    }

    public static void writeChar(IoBuffer buf, char value) {
        buf.putChar(value);
    }

    public static short readShort(IoBuffer buf) {
        return buf.getShort();
    }

    public static int readUnsignedShort(IoBuffer buf) {
        return buf.getUnsignedShort();
    }

    public static void writeShort(IoBuffer buf, short value) {
        buf.putShort(value);
    }

    public static int readInt(IoBuffer buf) {
        return buf.getInt();
    }

    public static long readUnsignedInt(IoBuffer buf) {
        return buf.getUnsignedInt();
    }

    public static void writeInt(IoBuffer buf, int value) {
        buf.putUnsignedInt(value);
    }

    public static long readLong(IoBuffer buf) {
        return buf.getLong();
    }

    public static void writeLong(IoBuffer buf, long value) {
        buf.putUnsignedInt(value);
    }

    public static float readFloat(IoBuffer buf) {
        return buf.getFloat();
    }

    public static void writeFloat(IoBuffer buf, float value) {
        buf.putFloat(value);
    }

    public static double readDouble(IoBuffer buf) {
        return buf.getDouble();
    }

    public static void writeDouble(IoBuffer buf, double value) {
        buf.putDouble(value);
    }


    /**读short长度的String*/
    public static String readShortString(IoBuffer buf) {
        int strSize = buf.getUnsignedShort();
        byte[] content = new byte[strSize];
        buf.get(content);
        try {
            return new String(content, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            return "";
        }
    }


    /**读byte长度的String*/
    public static String readByteString(IoBuffer buf) {
        int strSize = buf.getUnsigned();
        byte[] content = new byte[strSize];
        buf.get(content);
        try {
            return new String(content, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            return "";
        }
    }

    /**读1长度的bytelist*/
    public static String resdByteString(IoBuffer buf, String msg) {
        int strSize = buf.getUnsigned();
        byte[] content = new byte[strSize];
        buf.get(content);
        try {
            return new String(content, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            return "";
        }
    }

    public static String readFullString(IoBuffer buf) {
        int strSize = buf.getInt();
        byte[] content = new byte[strSize];
        buf.get(content);
        try {
            return new String(content, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            return "";
        }
    }

    /**写byte长度的String*/
    public static void writeByteString(IoBuffer buf, String msg) {
        byte[] content;
        try {
            if (msg == null) {
                msg = "";
            }
            content = msg.getBytes("GBK");
            buf.putUnsigned(content.length);
            System.out.println("length:"+content.length);
            System.out.println("context"+content);
            buf.put(content);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
    }

    /**写short长度的String*/
    public static void writeShortString(IoBuffer buf, String msg) {
        byte[] content;
        try {
            if (msg == null) {
                msg = "";
            }
            content = msg.getBytes("GBK");
            buf.putUnsignedShort(content.length);
            buf.put(content);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
    }

    public static void writeFullString(IoBuffer buf, String msg) {
        byte[] content;
        try {
            if (msg == null) {
                msg = "";
            }
            content = msg.getBytes("GBK");
            buf.putInt(content.length);
            buf.put(content);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
    }

    public static void writeUnsignedByte(IoBuffer buf, short value) {
        buf.putUnsigned(value);
    }

    public static void writeUnsignedInt(IoBuffer buf, long value) {
        buf.putUnsignedInt(value);
    }

    public static void writeUnsignedShort(IoBuffer buf, int value) {
        buf.putUnsignedShort(value);
    }
}
