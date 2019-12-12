package com.shsxt.crm.proxy;


import com.shsxt.crm.annotations.RequirePermission;
import com.shsxt.crm.utils.AssertUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 定义权限切面类
 */
@Component
@Aspect
public class PermissionProxy {

    @Autowired
    private HttpSession session;


    /**
     * 1.切入点
     * 2.通知
     *     前置
     *     返回
     *     异常
     *     最终
     *     环绕
     */

    // 拦截指定注解RequirePermission 标注的所有方法
    @Pointcut("@annotation(com.shsxt.crm.annotations.RequirePermission)")
    public void cut(){}


    @Around(value = "cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result=null;

        // 获取用户拥有的角色 所有权限码值
        List<String> aclValues = (List<String>) session.getAttribute("permissions");
        // 获取客户端访问的url 对应的方法 权限码值
       MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
       RequirePermission requirePermission = methodSignature.getMethod().getAnnotation(RequirePermission.class);
       String aclValue=requirePermission.aclValue();
       // 判断aclValues 是否包含当前方法权限码值 如果包含 目标方法放行  如果不包含 用户没有权限访问方法，方法禁行 环绕通知抛出没有权限异常
        AssertUtil.isTrue( !(aclValues.contains(aclValue)),"权限不足!");
        result = pjp.proceed();
        return result;
    }




}
