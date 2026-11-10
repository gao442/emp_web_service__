package com.itheima.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
//需要实现handlerInterceptor接口才能表明这是一个拦截器
public class LoginCheckInterceptor implements HandlerInterceptor {

    //执行controller前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url= request.getRequestURI();
        if(url==null || url.length()==0){
            Result error = Result.error("NOT_LOGIN");
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return false;
        }
        if(url.contains("login")){
            return true;
        }

        // 从请求头中获取 token（前端约定放在 header 的 "token" 字段）
        String token = request.getHeader("token");

        // 前置校验：token 为 null 或空串时直接返回未登录，
        // 避免把空值传给 JJWT 触发 IllegalArgumentException: JWT String argument cannot be null or empty
        if (token == null || token.isEmpty()) {
            Result error = Result.error("NOT_LOGIN");
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return false;
        }

        try {
            // 解析 token，若签名错误/已过期会抛异常，同样按未登录处理
            JwtUtils.parseJwt(token);
        } catch (Exception e) {
            // 这里只保留必要日志，避免刷屏堆栈
            System.out.println("[LoginCheck] JWT 解析失败: " + e.getMessage());
            Result error = Result.error("NOT_LOGIN");
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return false;
        }

        return true;
    }
    @Override
    //执行controller后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    //最后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
/*
两步操作先定义拦截器（实现拦截器的接口，重写三个方法）
，再配置拦截器
 */
/*
先过滤器filter然后是dispatchservlet
然后才是拦截器interceptor，最后才能到达controller层

回去的时候反着来
 */
/*
过滤器会拦截所有资源，而拦截器只会拦截spring里面的资源
 */