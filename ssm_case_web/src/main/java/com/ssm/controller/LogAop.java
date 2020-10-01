package com.ssm.controller;

import com.ssm.domain.SysLog;
import com.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author Harlan
 * @Date 2020/10/1
 */
@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest req;
    @Autowired
    private ISysLogService service;
    private Date visitTime;
    private Class clazz;
    private Method method;


    /**
     * 前置通知
     * 获取开始时间, 执行的类, 执行的方法
     * @param jp 切入点
     */
    @Before("execution(* com.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date();
        clazz = jp.getTarget().getClass();
        //获取访问方法的名称
        String methodName = jp.getSignature().getName();
        //获取访问方法的参数
        Object[] args = jp.getArgs();
        //获取执行方法对象
        if (args == null || args.length == 0){
            method = clazz.getMethod(methodName);
        }else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args.getClass();
            }
            method = clazz.getMethod(methodName,classArgs);
        }

    }

    /**
     * 后置通知
     * @param jp 切入点
     */
    @After("execution(* com.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        //获取执行时间
        Date now = new Date();
        long time = now.getTime() - visitTime.getTime();

        //获取url
        String url = "";
        if (clazz != null && method != null && clazz != LogAop.class){
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null){
                String classUrl = classAnnotation.value()[0];
                RequestMapping methodAnnotation =  method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null){
                    String methodUrl = methodAnnotation.value()[0];
                    url = classUrl + methodUrl;
                }
            }
        }

        //获取ip地址
        String ip = req.getRemoteAddr();

        //获取当权操作对象
        //从上下文中获取当前登录用户
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();

        //将日志相关信息封装到SysLog中
        SysLog sysLog = new SysLog();
        sysLog.setVisitTime(visitTime);
        sysLog.setExecutionTime(time);
        sysLog.setIp(ip);
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        sysLog.setMethod("[类名]" + clazz.getName() + "[方法民]" + method.getName());
        System.out.println(sysLog);

        //调用Service完成操作
        service.save(sysLog);
    }
}
