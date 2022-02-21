package com.test.mina.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteField {
    int value() default 0;//0：1字节描述长度；1：2字节描述长度
}
