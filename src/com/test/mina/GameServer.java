package com.test.mina;

import com.test.mina.codec.SerializerHelper;
import com.test.mina.message.MessageFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class GameServer {
    private static final Logger logger = LoggerFactory.getLogger(GameServer.class);
    public static void main(String[] args) throws Exception {
        run();
    }

    private static SocketAcceptor acceptor;
//    @Autowired
//    ServerService serverService;
//    @Autowired
//    ScheduledService scheduledService;

    public static void run(String... var1) throws Exception {
        initGameServer();
    }

    private static void initGameServer() throws Exception {
        logger.info("start server ......");
        MessageFactory.INSTANCE.initMessagePool("com.test.mina");

        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());

        Executor executor = Executors.newCachedThreadPool(new NamedThreadFactory("SOCKET-IO-PROCESSOR"));

        int coreSize = Runtime.getRuntime().availableProcessors();

        SimpleIoProcessorPool<NioSession> processor = new SimpleIoProcessorPool<NioSession>(NioProcessor.class, executor, coreSize, null);
        acceptor = new NioSocketAcceptor(processor);
        acceptor.setReuseAddress(true);
        SocketSessionConfig config = new DefaultSocketSessionConfig();
        config.setKeepAlive(false);
        config.setTcpNoDelay(true);
        config.setReuseAddress(true);
        config.setReadBufferSize(1024 * 2);
        config.setIdleTime(IdleStatus.BOTH_IDLE, 30000);
        config.setSoLinger(0);
        acceptor.getSessionConfig().setAll(config);
        DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
        filterChain.addLast("codec", new ProtocolCodecFilter(SerializerHelper.getInstance().getCodecFactory()));
//        filterChain.addLast("moduleEntrance", new ModuleEntranceFilter());
//        filterChain.addLast("msgTrace", new MessageTraceFilter());
//
//        filterChain.addLast("chat", new ChatMessageFilter());
//
//        filterChain.addLast("heartBeat", new HeartbeatFilter());
//
//        filterChain.addLast("moduleCounter", new ModuleCounterFilter());

        //指定业务逻辑处理器
        acceptor.setHandler(new ServerSocketIoHandler(MessageDispatcher.getInstance()));
        acceptor.setBacklog(128);

        acceptor.setDefaultLocalAddress(new InetSocketAddress(3456));
        acceptor.bind();
//        logger.warn("game server start at port:{},", serverService.getPort());

        logger.info("start success");
    }

    public SocketAcceptor getAcceptor() {
        return acceptor;
    }
}

