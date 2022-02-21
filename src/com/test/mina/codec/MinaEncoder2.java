package com.test.mina.codec;


import com.test.mina.codec.my.PacketEnc;
import com.test.mina.message.Message;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;


public class MinaEncoder2 implements ProtocolEncoder {
    static Logger logger = LoggerFactory.getLogger(MinaEncoder.class);

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

    public static IoBuffer writeMessage(Message message) {
        int msgId = message.getModule();
        IoBuffer buffer = IoBuffer.allocate(32).setAutoExpand(true).setAutoShrink(true);
        buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);

        // 写入协议号
        buffer.putShort((short)msgId);

        //写入具体内容
        IMessageEncoder msgEncoder = SerializerHelper.getInstance().getEncoder();

        byte[] body = msgEncoder.writeMessageBody(message);
        //加密
        //byte[] body2 = PacketEnc.encrypt(body);
        buffer.put(body);
        buffer.putShort((short)msgId);
        buffer.put(body);
        buffer.flip();
        buffer.rewind();

        return buffer;
    }
}
