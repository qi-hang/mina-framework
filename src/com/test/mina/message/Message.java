package com.test.mina.message;

import com.test.mina.annotation.MessageMeta;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * base class of IO message
 */
public abstract class Message implements Serializable {

    /**
     * messageMeta, module of message
     *
     * @return
     */
    @JsonIgnore
    public int getModule() {
        MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
        if (annotation != null) {
            return annotation.module();
        }
        return 0;
    }

    @JsonIgnore
    public String getDefine() {
        MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
        if (annotation != null) {
            return annotation.define();
        }
        return "";
    }

}
