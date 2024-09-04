package com.mrgao.thread.test.annotation;

import java.lang.annotation.*;

/**
 * @Description 自定义注解实现
 * @Author Mr.Gao
 * @Date 2024/9/4 22:49
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ToAsync {
    /**
     *
     * @return
     */
    String value() default "";
}
