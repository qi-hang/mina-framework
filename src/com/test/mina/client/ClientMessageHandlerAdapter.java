package com.test.mina.client;

import com.test.mina.GameServer;
import com.test.mina.codec.my.Convert;
import com.test.mina.message.*;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

public class ClientMessageHandlerAdapter implements IoHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandlerAdapter.class);
    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
//        ReqLogin reqLogin=new ReqLogin();
//        reqLogin.setUserName("test");
//        reqLogin.setPwd("pwd");
//        reqLogin.setLoginTime((long) 111);
//        Message message=(Message) reqLogin;
//        MessagePusher.pushMessage(ioSession,message);
//            TestMessage reqLogin=new TestMessage();
//            reqLogin.setStr1("test");
//            reqLogin.setBoolVal(true);
//            //reqLogin.setBytesVal((byte) 0x11);
//            reqLogin.setIntVal(11);
//            reqLogin.setDoubleVal(3.25);
//            //reqLogin.setLongval((long) 111);
//            Message message=(Message) reqLogin;
//            MessagePusher.pushMessage(ioSession,message);
//        SimpletestMessage reqLogin=new SimpletestMessage();
//        String ss="test";
//        reqLogin.setIntVal(114514);
//        reqLogin.setLen1str("test");
//        reqLogin.setBoolVal(true);
//        reqLogin.setDoubleVal(2.25);
//        long ll=277338678*66666;
//        reqLogin.setBoolVal2(true);
//        reqLogin.setLen2str("test2test");
//        byte[] isoret = ss.getBytes("GBK");
//        for(int i=0;i<isoret.length;i++){
//            System.out.print(isoret[i]+" ");
//        }
//        Message message=(Message) reqLogin;
//
//        SimpletestMessage reqLogin2=new SimpletestMessage();
//        reqLogin2.setIntVal(1);
//        reqLogin2.setLen1str("test");
//        reqLogin2.setBoolVal(true);
//        reqLogin2.setDoubleVal(0.25);
//        reqLogin2.setBoolVal2(true);
//        reqLogin2.setLen2str("test2");
//        Message message2=(Message) reqLogin2;
//        MessagePusher.pushMessage(ioSession,message);
//        MessagePusher.pushMessage(ioSession,message2);

    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
        //byte[] bytes={04,31,31,31,31,05,00,32,32,32,32,32,03,01,01,01,01,00,00,00,00,(byte)0xef,34,48,(byte)0xdf,99,39,76};
        //ioSession.write(bytes);
        int ss=2;
        //ioSession.write(bytes);
        System.out.println("open...");
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {

    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {

    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {

    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        logger.info(o.toString());
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println("sending...");

    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {

    }
}
