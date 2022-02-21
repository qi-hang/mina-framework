package com.test.mina.codec;


import com.test.mina.codec.reflect.ReflectDecoder;
import com.test.mina.codec.reflect.ReflectEncoder;

public class SerializerHelper {

    public static volatile SerializerHelper instance;

    private MessageCodecFactory codecFactory;

    private IMessageDecoder decoder;

    private IMessageEncoder encoder;

    public static SerializerHelper getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (SerializerHelper.class) {
            if (instance == null) {
                instance = new SerializerHelper();
                instance.initialize();
            }
        }
        return instance;
    }

    private void initialize() {
        decoder = new ReflectDecoder();
        encoder = new ReflectEncoder();
        codecFactory = new MessageCodecFactory();
    }

    public MessageCodecFactory getCodecFactory() {
        return codecFactory;
    }

    public IMessageDecoder getDecoder() {
        return decoder;
    }

    public IMessageEncoder getEncoder() {
        return encoder;
    }

}
