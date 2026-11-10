package com.itheima.config;

import com.itheima.Interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//声明这是个配置类
public class WebConfig implements WebMvcConfigurer {
    @Autowired//交给容器自动注入
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    //注册拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                //放行前端静态资源（页面、样式、脚本），避免被登录拦截
                .excludePathPatterns("/login.html", "/manage.html", "/index.html", "/upload.html", "/css/**", "/js/**");
    }
}
