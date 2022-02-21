package com.test.mina;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class MessageHandlerBeanProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        MessageDispatcher.getInstance().registerMessageHandler(o);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
