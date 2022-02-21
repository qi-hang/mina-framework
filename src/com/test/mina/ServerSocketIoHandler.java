package com.test.mina;

import com.test.mina.message.IMessageDispatcher;
import com.test.mina.message.Message;
import com.test.mina.message.MessageFactory;
import com.test.mina.task.ThreadLocalUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerSocketIoHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerSocketIoHandler.class);

    private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(16);

    /**
     * 消息分发器
     */
    private final IMessageDispatcher messageDispatcher;

    public ServerSocketIoHandler(IMessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void sessionOpened(final IoSession session) throws ClassNotFoundException {

    }

    @Override
    public void sessionClosed(IoSession session) {
        logger.info("关闭当前session：{}#{}", session.getId(), session.getRemoteAddress());

    }


    @Override
    public void sessionCreated(IoSession session) {
        logger.info("创建一个新连接：{}", session.getRemoteAddress());
    }

    @Override
    public void messageReceived(IoSession session, Object data) {
        Message message = (Message) data;
        //logger.info("messageReceived"+data);
        messageDispatcher.dispatch(session, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {

    }
}
