package com.test.mina.message;

import org.apache.mina.core.session.IoSession;


public interface IMessageDispatcher {

    /**
     * message entrance, in which io thread dispatch messages
     *
     * @param session
     * @param message
     */
    void dispatch(IoSession session, Message message);
}