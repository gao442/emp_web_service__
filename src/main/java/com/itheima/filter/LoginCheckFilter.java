package com.itheima.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

//@WebFilter // WebFilter注解用于声明这是一个过滤器，可以指定过滤的URL模式
public class LoginCheckFilter implements Filter { // 实现Filter接口，成为一个过滤器

    @Override
    /**
     * 执行过滤操作的核心方法
     * @param servletRequest 请求对象，封装了客户端的请求信息
     * @param servletResponse 响应对象，用于向客户端返回响应
     * @param filterChain 过滤器链，用于将请求传递给下一个过滤器或目标资源
     * @throws IOException 可能发生的I/O异常
     * @throws ServletException 可能发生的Servlet异常
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 将请求对象强制转换为HttpServletRequest，以便获取HTTP相关的特性
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        // 将响应对象强制转换为HttpServletResponse，以便设置HTTP响应
        HttpServletResponse httpServletResponse= (HttpServletResponse) servletResponse;
        //获取请求的url
        String url=httpServletRequest.getRequestURL().toString();
        // 如果请求URL包含"login"，则直接放行，不进行登录检查
        if(url.contains("login")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 从请求头中获取JWT令牌
        String jwt=httpServletRequest.getHeader("token");

        // 检查JWT令牌是否存在
        if( jwt==null ||jwt.length() ==0){
            // 创建错误结果对象，表示未登录
            Result err = Result.error("NOT_LOGIN");
            //手动转化为json格式的数据返回给前端
            String jsonString = JSONObject.toJSONString(err);
            //手动传递给前端
            httpServletResponse.getWriter().write(jsonString);
            return;
        }

        try {
            // 尝试解析JWT令牌，验证其有效性
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果JWT解析失败，创建错误结果对象，表示未登录
            Result err = Result.error("NOT_LOGIN");
            //手动转化为json格式的数据返回给前端
            String jsonString = JSONObject.toJSONString(err);
            //手动传递给前端
            servletResponse.getWriter().write(jsonString);
            return;

        }

        // 如果JWT验证通过，则继续执行过滤器链
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
