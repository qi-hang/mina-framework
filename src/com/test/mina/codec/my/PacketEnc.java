package com.test.mina.codec.my;

public class PacketEnc {

    public static byte[] decrypt(byte[] in) {
        for (int i = 0; i < in.length; i++) {

            in[i] = (byte)(in[i] & 0x33);
        }
        return in;
    }

    public static byte[] encrypt(byte[] in) {
        for (int i = 0; i < in.length; i++) {

            in[i] = (byte)(in[i] & 0x33);
        }
        return in;
    }



}
