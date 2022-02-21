package com.test.mina.codec.reflect;


import com.test.mina.codec.IMessageDecoder;
import com.test.mina.codec.my.Convert;
import com.test.mina.codec.reflect.serializer.Serializer;
import com.test.mina.message.Message;
import com.test.mina.message.MessageFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(ReflectDecoder.class);

    @Override
    public Message readMessage(int module, int cmd, byte[] body) {
        IoBuffer in = IoBuffer.allocate(body.length);
        logger.info("decoding...");
        in.put(body);
        in.flip();

        Class<?> msgClazz = MessageFactory.INSTANCE.getMessage(module, cmd);
        if (msgClazz == null) {
            logger.info("null in "+module);
            return null;
        }
        try {
            //logger.info("clazz:"+msgClazz);
            Serializer messageCodec = Serializer.getSerializer(msgClazz);
            //logger.info(messageCodec.getClass().toString()+" in:"+in.getHexDump());
            return (Message) messageCodec.decode(in, msgClazz, null);
        } catch (Exception e) {
            logger.error("读取消息出错, 协议号{}，类型{}, 异常{}", module, cmd, e);
        }
        return null;
    }
}
