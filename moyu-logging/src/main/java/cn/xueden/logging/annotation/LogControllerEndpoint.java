package cn.xueden.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.logging.annotation
 * @version:1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogControllerEndpoint {

    String operation() default "";

    String exceptionMessage() default "系统内部异常";
}
