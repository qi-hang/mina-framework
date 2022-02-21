package com.test.mina.annotation;

import java.lang.annotation.*;


@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

}