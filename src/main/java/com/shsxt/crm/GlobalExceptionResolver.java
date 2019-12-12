package com.shsxt.crm;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.exceptions.NoLoginException;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("msg", "服务器异常...");
        mv.addObject("code", 301);
        mv.addObject("ctx",request.getContextPath());

        /**
         * 判断异常类型是否为 未登录异常
         */
        if(ex instanceof NoLoginException){
            NoLoginException noLoginException= (NoLoginException) ex;
            mv.addObject("msg",noLoginException.getMsg());
            mv.setViewName("no_login");
            return mv;
        }


        /**
         *  1.方法返回视图
         *  2.方法返回json
         */

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethod().getAnnotation(ResponseBody.class);
            if (null == responseBody) {
                /**
                 * 方法返回视图
                 */
                if (ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    mv.addObject("msg", pe.getMsg());
                    mv.addObject("code", pe.getCode());
                }
                return mv;
            } else {
                /**
                 * 方法返回json
                 */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("服务器异常");

                if (ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    resultInfo.setMsg(pe.getMsg());
                    resultInfo.setCode(pe.getCode());
                }
                // 向浏览器输出 错误json 信息
                PrintWriter pw = null;
                response.setContentType("application/json;charset=utf-8");
                response.setCharacterEncoding("utf-8");
                try {
                    pw = response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != pw) {
                        pw.close();
                    }
                }
                return null;
            }
        } else {
            return mv;
        }
    }
}
