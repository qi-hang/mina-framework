package com.test.mina.codec.my;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Convert {
    public static String clearSpaces(String str) {
        return str.replaceAll("\\s+", "");
    }

    public static int bytes4ToIntBig(byte[] src) {
        int offset = 0;
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    public static int bytes2ToIntBig(byte[] src) {
        int offset = 0;
        int value;
        value = (int) (((src[offset] & 0xFF) << 8)
                | ((src[offset + 1] & 0xFF)));

        return value;
    }

    public static int bytes2ToIntLittle(byte[] src) {
        int offset = 0;
        int value;
        value = (int) (((src[offset+1] & 0xFF) << 8)
                | ((src[offset] & 0xFF)));

        return value;
    }

    public static byte[] intToBytes4Big(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] intToBytes2Big(int value) {
        byte[] src = new byte[2];
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] intToBytes2Little(int value) {
        byte[] src = new byte[2];
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static String bytesToHexStr(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexStrToBytes(String hexString) {

        hexString = hexString.toUpperCase().replaceAll("\\s+", "");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len / 2];

        while (index < len) {

            String sub = hexString.substring(index, index + 2);

            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);

            index += 2;
        }
        return bytes;

    }

    public static String currMstoDateStr(long msec1) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//将毫秒级long值转换成日期格式
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(msec1);
        String dateStr = dateformat.format(gc.getTime());
        return dateStr;
    }


    public static float getFloatReserveOne(float f) {
        BigDecimal b = new BigDecimal(f);
        float res = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return res;
    }

    public static float getFloatReserveTwo(float f) {
        BigDecimal b = new BigDecimal(f);
        float res = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return res;
    }


}
