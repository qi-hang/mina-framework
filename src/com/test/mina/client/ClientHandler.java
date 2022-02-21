package com.test.mina.client;

import com.test.mina.message.IMessageDispatcher;
import com.test.mina.message.Message;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.session.SessionProperties;

public class ClientHandler extends IoHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(ClientHandler.class);


    /**
     * 消息分发器
     */
    private IMessageDispatcher messageDispatcher;

    public ClientHandler(IMessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void sessionClosed(IoSession session)throws Exception{
        super.sessionClosed(session);
    }
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        //session.setAttributeIfAbsent(SessionProperties.DISTRIBUTE_KEY, SessionManager.INSTANCE.getNextDistributeKey());
    }

    @Override
    public void messageReceived(IoSession session, Object data) throws Exception {
        Message message = (Message) data;
        messageDispatcher.dispatch(session, message);
        logger.info("收到消息");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
//        logger.error("client exception", cause);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    }
}
