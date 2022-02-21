package com.test.mina.message;

import com.test.mina.codec.my.MyDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageOutput {
    static Logger logger = LoggerFactory.getLogger(MessageOutput.class);
    public void out(int value,Message msg){
        String module=MessageFactory.INSTANCE.getMessagemodule(value);
        for(int i=0;i<module.length();i++){

        }
    }
}
