package com.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//@WebFilter("/*")
public class DemoFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器拦截");
        //放行拦截的请求（疏通访问和响应操作）
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
/*
首先创建类来实现Fileter的接口，
然后加上两个注解@WebFilter（指定拦截的范围）和@ServletComponentScan（代表启动servlet支持）
最后重写方法就可以使用过滤器


放行后会访问对应资源，资源访问完成后还会回到过滤器中，
回到过滤器中会执行放行后的操作
 */