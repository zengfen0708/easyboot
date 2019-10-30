package com.zf.easyboot.aspect;


import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.enums.DateUnit;
import com.zf.easyboot.common.enums.LogTypeEnum;
import com.zf.easyboot.common.utils.*;
import com.zf.easyboot.modules.system.entity.LogEntity;
import com.zf.easyboot.modules.system.service.LogService;
import com.zf.easyboot.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 记录手动需要记录的日志信息
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/21.
 */
@Slf4j
@Aspect
@Component
public class LogAspect {


    @Autowired
    private LogService logService;

    private static final String START_TIME = "ZFLogREQUEST-START";

    /**
     * 配置切入点（只记录手动需要记录的日志)
     */
    @Pointcut("@annotation(com.zf.easyboot.common.annotation.SysLog)")
    public void logPointCut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }


    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        HttpServletRequest request = getHttpServletRequest();
        //获取当前时间
        LocalDateTime startTime = LocalDateTime.now();
        request.setAttribute(START_TIME, startTime);

        result = joinPoint.proceed();

        //请求时间
        long time = DateUtil.betweenTime(startTime, LocalDateTime.now(), DateUnit.Millis);

        LogEntity logEntity = new LogEntity();
        logEntity.setLogType(LogTypeEnum.SUSSESS.getCode());
        logEntity.setTime(time);
        logEntity.setRequestIp(IPUtils.getIpHost(request));

        this.saveRequestLog(logEntity, joinPoint);

        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = getHttpServletRequest();

        LocalDateTime startTime = (LocalDateTime) request.getAttribute(START_TIME);
        //请求时间
        long time = DateUtil.betweenTime(startTime, LocalDateTime.now(), DateUnit.Millis);

        LogEntity logEntity = new LogEntity();
        logEntity.setLogType(LogTypeEnum.ERROR.getCode());
        logEntity.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        logEntity.setRequestIp(IPUtils.getIpHost(request));
        logEntity.setTime(time);

        this.saveRequestLog(logEntity, (ProceedingJoinPoint) joinPoint);
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

    /**
     * 保存请求日志信息
     *
     * @param logEntity
     */
    private void saveRequestLog(LogEntity logEntity, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);

        if (sysLog != null) {
            logEntity.setDescription(sysLog.value());
        }

        logEntity.setUsername(SecurityUtil.getCurrentUsername());
        // 方法路径
        String methodName = methodSignature.getDeclaringTypeName() + "." + methodSignature.getName();
        logEntity.setMethod(methodName);
        String params = ConverterConstant.converterStr.convert(RequestMethodUtils.getParameter(method, joinPoint.getArgs()));
        logEntity.setParams(params);
        //保存请求日志
        logService.saveLog(logEntity);
    }

}
