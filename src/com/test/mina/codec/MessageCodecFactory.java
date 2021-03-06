package com.test.mina.codec;

import com.test.mina.codec.my.MyDecoder;
import com.test.mina.codec.my.v1.MyEncoder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


public class MessageCodecFactory implements ProtocolCodecFactory {

    private MyDecoder decoder = new MyDecoder();

    private MyEncoder encoder = new MyEncoder();

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
}
