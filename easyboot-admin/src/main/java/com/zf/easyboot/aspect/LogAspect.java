package com.zf.easyboot.aspect;

import com.zf.easyboot.common.enums.DateUnit;
import com.zf.easyboot.common.utils.DateUtil;
import com.zf.easyboot.common.utils.IPUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 使用aop记录请求日志信息
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/17.
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private static final String START_TIME = "ZFREQUEST-START";

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.zf..controller.*.*(..))") //两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {
    }


    /**
     * 前置操作
     *
     * @param point
     */
    @Before("logPointCut()")
    public void beforeLog(JoinPoint point) {
        HttpServletRequest request = getHttpServletRequest();
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        log.info("【{}请求URL】:{}", request.getMethod(), request.getRequestURL());
        log.info("【请求IP】:{}", IPUtils.getIpHost(request));
        log.info("【请求类名】:{},【请求方法名】:{}",
                point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName());

        log.info("【浏览器类型】:{},【操作系统】:{}，" +
                        "【原始User-Agent】:{}", userAgent.getBrowser().toString(),
                userAgent.getOperatingSystem().toString(), header);
        //获取当前时间
        LocalDateTime startTime = LocalDateTime.now();
        request.setAttribute(START_TIME, startTime);
    }


    /**
     * 后置操作
     */
    @AfterReturning("logPointCut()")
    public void afterReturning() {
        HttpServletRequest request = getHttpServletRequest();

        LocalDateTime startTime = (LocalDateTime) request.getAttribute(START_TIME);
        //结束时间
        long time = DateUtil.betweenTime(startTime, LocalDateTime.now(), DateUnit.Millis);

        log.info("【接口耗时】：{}毫秒", time);
    }


    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("logPointCut()")
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
