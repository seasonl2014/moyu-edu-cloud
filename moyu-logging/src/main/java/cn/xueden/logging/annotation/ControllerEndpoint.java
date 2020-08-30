package cn.xueden.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**功能描述：自定义日志注解
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/15
 * @Description:cn.xueden.logging.annotation
 * @version:1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerEndpoint {

    String operation() default "";

    String exceptionMessage() default "系统内部异常";


}
