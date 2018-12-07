package com.tj720.controller.framework.auth;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerAop {
    String url() default "";

    /** 动作的名称 */
    String action() default "";
}
