package com.test.mina;

import com.test.mina.annotation.MessageMeta;
import com.test.mina.annotation.RequestMapping;
import com.test.mina.message.CmdExecutor;
import com.test.mina.message.IMessageDispatcher;
import com.test.mina.message.Message;
import com.test.mina.task.MessageTask;
import com.test.mina.task.TaskHandlerContext;
import org.apache.catalina.Role;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class MessageDispatcher implements IMessageDispatcher {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static MessageDispatcher instance = new MessageDispatcher();


    public static MessageDispatcher getInstance() {
        return instance;
    }


    /**
     * [module_cmd, CmdExecutor]
     */
    private static final Map<String, CmdExecutor> MODULE_CMD_HANDLERS = new HashMap<>();

    public void registerMessageHandler(Object controller) {
        try {
            Method[] methods = controller.getClass().getDeclaredMethods();
            for (Method method : methods) {
                RequestMapping mapperAnnotation = method.getAnnotation(RequestMapping.class);
                if (mapperAnnotation != null) {
                    String meta = getMessageMeta(method);
                    if (meta == null) {
                        logger.error("methodName = " + method.getName());
                        throw new RuntimeException(String.format("controller[%s] method[%s] lack of RequestMapping annotation",
                                controller.getClass().getSimpleName(), method.getName()));
                    }
                    CmdExecutor cmdExecutor = MODULE_CMD_HANDLERS.get(meta);
                    if (cmdExecutor != null) {
//                            throw new RuntimeException(String.format("module[%d] cmd[%d] duplicated", module, cmd));
                    }

                    cmdExecutor = CmdExecutor.valueOf(method, method.getParameterTypes(), controller);
                    MODULE_CMD_HANDLERS.put(meta, cmdExecutor);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 返回方法所带Message参数的元信息
     *
     * @param method
     * @return
     */
    private String getMessageMeta(Method method) {
        for (Class<?> paramClazz : method.getParameterTypes()) {
            if (Message.class.isAssignableFrom(paramClazz)) {
                MessageMeta protocol = paramClazz.getAnnotation(MessageMeta.class);
                if (protocol != null) {
                    return String.valueOf(protocol.module());
                }
            }
        }
        return null;
    }

    @Override
    public void dispatch(IoSession session, Message message) {
        int module = message.getModule();
        CmdExecutor cmdExecutor = MODULE_CMD_HANDLERS.get(String.valueOf(module));
        if (cmdExecutor == null) {
            logger.error("message executor missed, module={}", module);
            return;
        }
        Object[] params = convertToMethodParams(session, cmdExecutor.getParams(), message);
        Object controller = cmdExecutor.getHandler();

        int distributeKey = 0;
//        Role role = SessionUtils.getRoleBySession(session);
//        if (role != null) {
//            distributeKey = role.getDistributeKey();
//        } else {
//            distributeKey = (Integer) session.getAttribute(SessionProperties.DISTRIBUTE_KEY);
//        }
        TaskHandlerContext.INSTANCE.acceptTask(MessageTask.valueOf(distributeKey, controller, cmdExecutor.getMethod(), params, message));
    }

    /**
     * 将各种参数转为被RequestMapper注解的方法的实参
     *
     * @param session
     * @param methodParams
     * @param message
     * @return
     */
    private Object[] convertToMethodParams(IoSession session, Class<?>[] methodParams, Message message) {
        Object[] result = new Object[methodParams == null ? 0 : methodParams.length];

        for (int i = 0; i < result.length; i++) {
            Class<?> param = methodParams[i];
            result[i] = session;//
//            if (IoSession.class.isAssignableFrom(param)) {
//                result[i] = session;
//            } else if (Long.class.isAssignableFrom(param)) {
//                result[i] = SessionManager.INSTANCE.getPlayerIdBy(session);
//            } else if (long.class.isAssignableFrom(param)) {
//                result[i] = SessionManager.INSTANCE.getPlayerIdBy(session);
//            } else if (Role.class.isAssignableFrom(param)) {
//                Role role = SessionUtils.getRoleBySession(session);
//                result[i] = role;
//            } else if (Message.class.isAssignableFrom(param)) {
//                result[i] = message;
//            }
        }
        return result;
    }

}
