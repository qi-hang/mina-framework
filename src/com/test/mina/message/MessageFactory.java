package com.test.mina.message;


import com.test.mina.ServerSocketIoHandler;
import com.test.mina.annotation.MessageMeta;
import com.test.utils.ClassScanner;
import org.nutz.mvc.upload.FieldMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum MessageFactory {

    /**
     * 枚举单例
     */
    INSTANCE;

    private Map<String, Class<?>> id2Clazz = new HashMap<>();
    private Map<Integer, Class<?>> module2Clazz = new HashMap<>();

    private Map<Class<?>, String> clazz2Id = new HashMap<>();

    private Map<Integer, String> xieyi2module = new HashMap<>();


    /**
     * scan all message class and register into pool
     */
    private static final Logger logger = LoggerFactory.getLogger(MessageFactory.class);
    public void initMessagePool(String scanPath) throws ClassNotFoundException {
        Set<Class<?>> messages = ClassScanner.listAllSubclasses(scanPath, Message.class);
        for (Class<?> clazz : messages) {
            MessageMeta meta = clazz.getAnnotation(MessageMeta.class);
            String keys="";
            if (meta == null) {
                throw new RuntimeException("messages[" + clazz.getSimpleName() + "] missed MessageMeta annotation");
            }
            logger.debug("@@"+clazz.toString().substring(6));
            Class newclass=Class.forName(clazz.toString().substring(6));
            MessageMeta declaredAnnotation = clazz.getDeclaredAnnotation(MessageMeta.class);
//            if(declaredAnnotation != null){
//                System.out.println("类Product的注解@Alias的value值是："+declaredAnnotation.module());
//            }
            for (Field field : newclass.getDeclaredFields()) {
                try {
                    //get field name and value.
                    if(field.getName().contains("len1str")){
                        keys+="s";//
                    }else if(field.getName().contains("len2str")){
                        keys+="S";//
                    }else if(field.getName().contains("boolVal")){
                        keys+="b";//
                    }else if(field.getName().contains("intVal")){
                        keys+="i";//
                    }else if(field.getName().contains("doubleVal")){
                       keys+="d";
                    }else if(field.getName().contains("len1byte")){
                        keys+="u";//
                    }else if(field.getName().contains("len2byte")){
                        keys+="U";//
                    }else if(field.getName().contains("longval")){
                        keys+="l";
                    }else if(field.getName().contains("uintval")){
                        keys+="I";
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            String key = buildKey(meta.module(), meta.cmd());
            if (id2Clazz.containsKey(key)) {
                System.out.println("消息号重复：" + clazz.getSimpleName());
                throw new RuntimeException("message meta [" + key + "] duplicate！！");
            }
            id2Clazz.put(keys, clazz);
            clazz2Id.put(clazz, keys);
            xieyi2module.put(declaredAnnotation.module(),keys);
            module2Clazz.put(declaredAnnotation.module(),clazz);
            logger.info("已初始化方法:"+keys+"##"+clazz.toString());
        }
    }


    public Class<?> getMessage(int module, int cmd) {
        //return id2Clazz.get(buildKey(module, cmd));
        return module2Clazz.get(module);
    }

    public Class<?> getMessage(int id) {
        short module = (short) (id / 1000);
        short cmd = (short) (id % 1000);
        return id2Clazz.get(buildKey(module, cmd));
    }

    public String getMessageId(Class<?> clazz) {
        return clazz2Id.get(clazz);
    }

    public String getMessagemodule(int i) {
        return xieyi2module.get(i);
    }

    private String buildKey(int module, int cmd) {
        return module + "_" + cmd;
    }

}
