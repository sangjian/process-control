package cn.ideabuffer.process.annotation.model;

import java.lang.annotation.*;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ResourceModel {

    String id() default "";

    String name() default "";

    String description() default "";

}
