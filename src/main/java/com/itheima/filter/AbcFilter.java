package com.itheima.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//@WebFilter("/login/")
public class AbcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //filterChain.doFilter(servletRequest, servletResponse);

    }


}
/*
过滤器链的执行顺序 是按照类名的自然排序来排序的
 */
