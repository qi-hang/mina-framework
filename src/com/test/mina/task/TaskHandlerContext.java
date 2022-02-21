package com.test.mina.task;

import com.test.mina.NamedThreadFactory;
import com.test.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public enum TaskHandlerContext {
    /**
     * 枚举单例
     */
    INSTANCE;

    private static Logger logger = LoggerFactory.getLogger(TaskHandlerContext.class);
    private final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * task worker pool
     */
    private final List<TaskWorker> workerPool = new ArrayList<>();

    private final AtomicBoolean run = new AtomicBoolean(true);

    public void initialize() {
        int size = CORE_SIZE;
//        if (size > 16) {
//            size = 16;
//        }
//        int size = 32;
        logger.warn("初始化线程：" + size);
        for (int i = 1; i <= size; i++) {
            TaskWorker worker = new TaskWorker(i);
            workerPool.add(worker);
            new NamedThreadFactory("message-task-handler_" + i).newThread(worker).start();
        }
    }

    /**
     * @param task
     */
    public void acceptTask(AbstractDistributeTask task) {
        if (task == null) {
            throw new NullPointerException("task is null");
        }
        int distributeKey = task.distributeKey() % workerPool.size();
        workerPool.get(distributeKey).addTask(task);
    }

    /**
     * shut context
     */
    public void shutDown() {
        run.set(false);
        //LoggerUtils.error("关闭业务场景线程结束");
    }


    public String statistics() {
        Map<Integer, Integer> map = new HashMap<>();

        for (TaskWorker worker : workerPool) {
            map.put(worker.id, worker.taskQueue.size());
        }

        return JsonUtils.map2String(map);
    }

    private class TaskWorker implements Runnable {

        /**
         * worker id
         */
        private int id;
        /**
         * task consumer queue
         */
        private BlockingQueue<AbstractDistributeTask> taskQueue = new LinkedBlockingQueue<>();

        TaskWorker(int index) {
            this.id = index;
        }

        public void addTask(AbstractDistributeTask task) {
            this.taskQueue.add(task);
        }

        @Override
        public void run() {
            //accept task all the time
            while (run.get()) {
                try {
                    AbstractDistributeTask task = taskQueue.take();
                    task.markStartMillis();
                    task.action();
                    task.markEndMillis();

                    //if it is TimerTask and should run again, add it to queue
                    if (task instanceof TimerTask) {
                        TimerTask timerTask = (TimerTask) task;
                        if (timerTask.canRunAgain()) {
                            addTask(task);
                        }
                    }
                } catch (Exception e) {
                    logger.error("task Worker" + id, e);
                }
            }
        }
    }
}
