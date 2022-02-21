package com.test.mina.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LongField {

    int value() default 0; //为1长度8字节，默认没有

}
