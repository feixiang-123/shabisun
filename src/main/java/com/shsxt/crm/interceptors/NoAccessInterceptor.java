package com.shsxt.crm.interceptors;

import com.shsxt.crm.exceptions.NoLoginException;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoAccessInterceptor  extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 获取cookie 用户id
         *    userId ==null  || 数据库记录不存在 用户不存在|未登录  重定向到index
         */
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
      /*  if(null == userId || null ==userService.queryById(userId)){
            // 执行页面重定向
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }*/
       if(null == userId || null ==userService.queryById(userId)){
           throw  new NoLoginException();
       }

        return true;
    }
}
