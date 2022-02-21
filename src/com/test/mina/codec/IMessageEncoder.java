package com.test.mina.codec;


import com.test.mina.message.Message;


public interface IMessageEncoder {

    /**
     * 把一个具体的消息序列化byte[]
     *
     * @param message
     * @return
     */
    byte[] writeMessageBody(Message message);

}
