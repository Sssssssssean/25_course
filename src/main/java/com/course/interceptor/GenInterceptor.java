package com.course.interceptor;

import com.course.utils.Constant;
import com.course.utils.RequestHeaderUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是登录请求，直接放行
        if (request.getRequestURI().endsWith("/account/login")) {
            return true;
        }
        
        // 首先尝试从请求头获取
        String header = request.getHeader(Constant.USER_HEADER_ID);
        
        // 如果请求头中没有，则尝试从URL参数获取
        if (header == null || header.isEmpty()) {
            header = request.getParameter(Constant.USER_HEADER_ID);
        }
        
        // 判断如果不携带id就拦截
        if (header == null || header.isEmpty()) {
            System.out.println(request.getRequestURI() + " no pass");
            return false;
        }
        
        // 存到threadlocal
        RequestHeaderUtil.setHeader(header);
        System.out.println("user request: " + request.getRequestURI() + " id: " + header);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理后执行
        System.out.println("Post Handle method is Calling");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求完成后执行
        System.out.println("Request and Response is completed");
        // 清理ThreadLocal
        RequestHeaderUtil.clear();
    }
}
