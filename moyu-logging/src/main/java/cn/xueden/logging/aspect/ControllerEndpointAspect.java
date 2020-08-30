package cn.xueden.logging.aspect;

import cn.xueden.common.entity.Log;
import cn.xueden.common.utils.AddressUtil;
import cn.xueden.common.utils.IPUtil;
import cn.xueden.common.utils.JWTUtils;
import cn.xueden.logging.annotation.ControllerEndpoint;
import cn.xueden.logging.service.ILogService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**功能描述：系统日志切面类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/15
 * @Description:cn.xueden.logging.aspect
 * @version:1.0
 */
@Slf4j
@Aspect
@Component
public class ControllerEndpointAspect extends  AspectSupport {

    private Log sysLog = new Log();

    private long startTime;

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(cn.xueden.logging.annotation.ControllerEndpoint)")
    public void pointcut(){}

    /**
     * 环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object saveSysLog(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result = null;

        //开始时间
        startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();

        Method method = signature.getMethod();

        //获取注解
        ControllerEndpoint controllerEndpoint = method.getAnnotation(ControllerEndpoint.class);
        if(controllerEndpoint!=null){
            String operation =  controllerEndpoint.operation();
            sysLog.setOperation(operation);
        }

        //请求的参数
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        sysLog.setParams("paramName:"+ Arrays.toString(paramNames)+",args"+Arrays.toString(args));

        //请求的IP地址
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddr = IPUtil.getIpAddr(request);
        sysLog.setIp(ipAddr);

        //地理位置
        sysLog.setLocation(AddressUtil.getCityInfo(ipAddr));

        //操作人
        String token = request.getHeader("Authorization");
        String username = JWTUtils.getUsername(token);
        sysLog.setUsername(username);

        //添加时间
        sysLog.setCreateTime(new Date());

        //执行目标方法
        result = joinPoint.proceed();

        //请求的方法名

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        sysLog.setMethod(className+"."+methodName+"()\n"
        +"\nresponse:"+postHandle(result));

        //执行耗时
        sysLog.setTime(System.currentTimeMillis()-startTime);

        //保存系统日志
        logService.saveLog(sysLog);

        return result;
    }

    /**
     * 返回数据
     * @param retVal
     * @return
     */
    private String postHandle(Object retVal){
        if(null==retVal){
            return "";
        }
        return JSON.toJSONString(retVal);
    }

}
