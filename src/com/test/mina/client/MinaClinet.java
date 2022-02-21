package com.test.mina.client;

import com.test.mina.NamedThreadFactory;
import com.test.mina.codec.MessageCodecFactory;
import com.test.mina.codec.SerializerHelper;
import com.test.mina.message.Message;
import com.test.mina.message.MessagePusher;
import com.test.mina.message.SimpletestMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MinaClinet {
    private SocketConnector connector;
    private ConnectFuture future;
    private IoSession session;
    public static void main(String[] args) {
        MinaClinet server = new MinaClinet();
        server.connect();
    }
    public boolean connect() {
        
        // 创建一个socket连接
        connector = new NioSocketConnector();
        // 设置链接超时时间
        connector.setConnectTimeoutMillis(3000);
        // 获取过滤器链
        DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
        // 添加编码过滤器 处理乱码、编码问题
        filterChain.addLast("codec", new ProtocolCodecFilter(new MessageCodecFactory()));
        //filterChain.addLast("codec", new ProtocolCodecFilter(SerializerHelper.getInstance().getCodecFactory()));
        /*
        // 日志
        LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
        loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
        filterChain.addLast("loger", loggingFilter);*/

        // 消息核心处理器
        connector.setHandler(new ClientMessageHandlerAdapter());
        // 连接服务器，知道端口、地址
        future = connector.connect(new InetSocketAddress(3456));
        // 等待连接创建完成
        future.awaitUninterruptibly();
        // 获取当前session
        session = future.getSession();

//
//        SimpletestMessage reqLogin=new SimpletestMessage();
//        String ss="test";
//        reqLogin.setIntVal(114514);
//        reqLogin.setLen1str("test");
//        reqLogin.setBoolVal(true);
//        reqLogin.setDoubleVal(2.25);
//        long ll=277338678*66666;
//        reqLogin.setBoolVal2(true);
//        reqLogin.setLen2str("test2test");
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
//        session.write(message);
        //session.write(message2);
        return true;
    }

    public void setAttribute(Object key, Object value) {
        session.setAttribute(key, value);
    }

    public void send(String message) {
        session.write(message);
    }

    public boolean close() {
        CloseFuture future = session.getCloseFuture();
        future.awaitUninterruptibly(1000);
        connector.dispose();
        return true;
    }

    public SocketConnector getConnector() {
        return connector;
    }

    public IoSession getSession() {
        return session;
    }
}
