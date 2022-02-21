package com.test.mina.codec.my;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.mina.core.buffer.IoBuffer;

import java.util.HashMap;
import java.util.Map;

/**
 * StanUtils 2.5x版本加解密包
 *
 * @author : xxx
 * @version : 1.0
 **/
public class MyUtils {
    public static Map<String, Integer> fmtLenMap = new HashMap<>();
    public static Map<Integer, String> DEFINE_MAP = new HashMap<>();
    static {
        fmtLenMap.put("i", 4);//int//ok
        fmtLenMap.put("d", 8);//double//ok
        fmtLenMap.put("b", 1);//boolean//ok
        fmtLenMap.put("I", 4);//unsignedint
        fmtLenMap.put("s", 1);//len1str//ok
        fmtLenMap.put("S", 2);//len2str//ok
        fmtLenMap.put("U", 2);//len2byte//ok
        fmtLenMap.put("u", 1);//len1byte//ok
        fmtLenMap.put("C", 1);
        fmtLenMap.put("H", 2);//short
        fmtLenMap.put("c", 1);//byte
        fmtLenMap.put("l", 8);//long//new
        //System.out.println("fmtLenMap###");
        buildDefineMap();
    }

    public static void buildDefineMap() {

    }
    public static int getDeineLen(String define) {
        return -1;
    }
    public static byte[] unsignedShortToByte2(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    public static byte[] unsignedIntToByte4(long s) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (s >> 24 & 0xFF);
        targets[1] = (byte) (s >> 16 & 0xFF);
        targets[2] = (byte) (s >> 8 & 0xFF);
        targets[3] = (byte) (s & 0xFF);
        return targets;
    }

    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) (value & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[3] = (byte) ((value >> 24) & 0xFF);
        return src;
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 字节转十六进制
     *
     * @param b 需要进行转换的byte字节
     * @return 转换后的Hex字符串
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }


    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 将IoBuffer转换成byte
     *
     * @param message
     */
    public static byte[] ioBufferToByte(Object message) {
        if (!(message instanceof IoBuffer)) {
            return null;
        }
        IoBuffer ioBuffer = (IoBuffer) message;
        byte[] b = new byte[ioBuffer.limit()];
        ioBuffer.get(b);
        return b;
    }

}
