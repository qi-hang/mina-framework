package com.test.mina.task;

import org.apache.catalina.Role;
import org.apache.mina.core.session.IoSession;

public class ThreadLocalUtil {

    public static void addLocalTask(Role role, Runnable task) {
        if (role == null) {
            throw new NullPointerException("role is null");
        }
        TaskHandlerContext.INSTANCE.acceptTask(new AbstractDistributeTask() {
            //int distributeKey = role.getDistributeKey();
            int distributeKey = 2;
            @Override
            public int distributeKey() {
                return distributeKey;
            }

            @Override
            public void action() {
                task.run();
            }
        });
    }

}
