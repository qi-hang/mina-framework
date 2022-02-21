package com.test.mina.codec.reflect;

import com.test.mina.codec.IMessageEncoder;
import com.test.mina.codec.reflect.serializer.Serializer;
import com.test.mina.message.Message;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectEncoder implements IMessageEncoder {

    private static Logger logger = LoggerFactory.getLogger(ReflectEncoder.class);

    @Override
    public byte[] writeMessageBody(Message message) {
        IoBuffer out = IoBuffer.allocate(32).setAutoShrink(true).setAutoExpand(true);
        //写入具体消息的内容
        try {
            Serializer messageCodec = Serializer.getSerializer(message.getClass());
            //System.out.println("xx"+messageCodec.);
            messageCodec.encode(out, message, null);
        } catch (Exception e) {
            logger.error("读取消息出错, 协议号{}, 类型{}, 异常{}", message.getModule(), e);
        }
        out.flip();
        byte[] body = new byte[out.remaining()];
        out.get(body);
        return body;
    }

}
