package com.test.mina.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageMeta {

    int module() default 0;

    int cmd() default 0;

    String define() default "";

}
