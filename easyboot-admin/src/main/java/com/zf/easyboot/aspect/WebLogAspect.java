package com.zf.easyboot.aspect;

import com.zf.easyboot.common.enums.DateUnit;
import com.zf.easyboot.common.utils.DateUtil;
import com.zf.easyboot.common.utils.IPUtils;
import com.zf.easyboot.common.utils.RequestMethodUtils;
import com.zf.easyboot.security.utils.SecurityUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 使用aop记录请求日志信息
 * 前置通知（Before）：在目标方法调用前调用通知功能；
 * 后置通知（After）：在目标方法调用之后调用通知功能，不关心方法的返回结果；
 * 返回通知（AfterReturning）：在目标方法成功执行之后调用通知功能；
 * 异常通知（AfterThrowing）：在目标方法抛出异常后调用通知功能；
 * 环绕通知（Around）：通知包裹了目标方法，在目标方法调用之前和之后执行自定义的行为。
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/17.
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    private static final String START_TIME = "ZFREQUEST-START";

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.zf..controller.*.*(..))") //两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void webLogPointCut() {
    }


    /**
     * 前置操作
     *
     * @param point
     */
    @Before("webLogPointCut()")
    public void beforeLog(JoinPoint point) {
        HttpServletRequest request = getHttpServletRequest();
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        log.info("【{}请求URL】:{}", request.getMethod(), request.getRequestURL());
        log.info("【请求IP】:{}", IPUtils.getIpHost(request));
        log.info("【浏览器类型】:{},【操作系统】:{}，" +
                        "【原始User-Agent】:{}", userAgent.getBrowser().toString(),
                userAgent.getOperatingSystem().toString(), header);
        log.info("【请求类名】:{},【请求方法名】:{}\n[请求参数]:{}",
                point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(),
                RequestMethodUtils.getParameter(method,point.getArgs()));


        //获取当前时间
        LocalDateTime startTime = LocalDateTime.now();
        request.setAttribute(START_TIME, startTime);
    }


    /**
     * 后置操作
     */
    @AfterReturning("webLogPointCut()")
    public void afterReturning() {
        HttpServletRequest request = getHttpServletRequest();

        LocalDateTime startTime = (LocalDateTime) request.getAttribute(START_TIME);
        //结束时间
        long time = DateUtil.betweenTime(startTime, LocalDateTime.now(), DateUnit.Millis);

        log.info("当前请求用户:{},[接口耗时]:{}毫秒", SecurityUtil.getCurrentUsername(), time);
    }


    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("webLogPointCut()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();

        //如何有需求
      /*  if (log.isInfoEnabled()) {
            log.info("返回信息:{}", JSON.toJSONString(result));
        }*/
        return result;
    }

    /**
     * 获取httpReuest请求
     *
     * @return
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(requestAttributes).getRequest();
    }



}
