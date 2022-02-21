package com.test.mina.task;

import com.test.mina.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


public class MessageTask extends AbstractDistributeTask {

    private static Logger logger = LoggerFactory.getLogger(MessageTask.class);

    /**
     * owner playerId
     */
    private long playerId;
    /**
     * io message content
     */
    private Message message;
    /**
     * message controller
     */
    private Object handler;
    /**
     * target method of the controller
     */
    private Method method;
    /**
     * arguments passed to the method
     */
    private Object[] params;

    public static MessageTask valueOf(int distributeKey, Object handler,
                                      Method method, Object[] params, Message message) {
        MessageTask msgTask = new MessageTask();
        msgTask.distributeKey = distributeKey;
        msgTask.handler = handler;
        msgTask.method = method;
        msgTask.params = params;
        msgTask.message = message;

        return msgTask;
    }

    @Override
    public void action() {
        try {
            method.invoke(handler, params);
        } catch (Throwable e) {
            logger.error("messageTask execute failed==协议执行失败=={}", message.getModule(), e);
        }
    }

    public long getPlayerId() {
        return playerId;
    }

    public Message getMessage() {
        return message;
    }

    public Object getHandler() {
        return handler;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    @Override
    public String toString() {
        return this.getName() + "[" + handler.getClass().getName() + "@" + method.getName() + "]";
    }

}
