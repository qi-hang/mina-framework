package com.test.mina.codec;


import com.test.mina.message.Message;


public interface IMessageDecoder {

    /**
     * 根据消息元信息反序列号为消息
     *
     * @param module
     * @param cmd
     * @param body
     * @return
     */
    Message readMessage(int module, int cmd, byte[] body);

}
