package com.test.mina.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListField {

    int value() default 0;// 0：2字节描述长度；1：1字节描述长度；2：不要长度；3：4字节描述长度

}
