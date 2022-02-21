package com.test.mina.codec.my.v1;


import com.test.mina.codec.CodecContext;
import com.test.mina.codec.IMessageEncoder;
import com.test.mina.codec.MinaEncoder;
import com.test.mina.codec.SerializerHelper;
import com.test.mina.codec.my.PacketEnc;
import com.test.mina.message.Message;


import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class MyEncoder implements ProtocolEncoder {


    static Logger  logger = LoggerFactory.getLogger(MyEncoder.class);

    @Override
    public void dispose(IoSession arg0) throws Exception {

    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
//        CodecContext context = (CodecContext) session.getAttribute(SessionProperties.CODEC_CONTEXT);
//        if (context == null) {
//            context = new CodecContext();
//            session.setAttribute(SessionProperties.CODEC_CONTEXT, context);
//        }
        IoBuffer buffer = writeMessage((Message) message);
        out.write(buffer);
    }

    private IoBuffer writeMessage(Message message) {
        return MinaEncoder.writeMessage(message);
    }

}
