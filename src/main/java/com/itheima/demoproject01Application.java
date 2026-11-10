package com.itheima;

import com.itheima.config.BeanConfig;
import com.itheima.pojo.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


//用import导入的类回直接被加载到spring的ioc容器里面
//可以导入普通类，配置类，和ImportSelect接口的实现类
//这个接口的实现类需要返回要导入ioc的全类名所构成的数组
//将目标类交给ioc容器进行管理
//导入的配置类里面的方法需要new一个并且返回放到ioc容器里的对象
//@Import({Result.class})
//@Import({BeanConfig.class})

//一般第三方依赖都会实现一个数值EnableXXX直接@EnableXXX就可以
//EnableXXX注解里面封装了Import注解

@ServletComponentScan//当前项目开启了对servlet组件的支持
@SpringBootApplication
//只扫描当前包及其子包的下的conponent

//当前类也是给配置类，因此可以在当前类声明第三方的bean对象

//可以指定扫描包的范围，扫描到才能进入ioc容器，
// 第三方的bean对象和第三方的配置类也必须被扫描到才能进入ioc容器
//@ComponentScan("{com.example,com.itheima}")
public class demoproject01Application {

    public static void main(String[] args) {
        SpringApplication.run(demoproject01Application.class, args);
    }

}

/*
spring boot：
起步依赖的原理是maven的依赖传递

自动配置是指spring boot 的 ioc容器里面放好了我们需要的bean对象
需要的时候直接进行注入就可以了
自动配置的原理：
第三方通过bean写好了需要返回的类对象，
再通过EnableXXX中的@Import注解，实现类里面返回其全类名的数组让spring框架对其进行扫描
从而完成自动配置
 */

/*
import加载的文件里面不是所有的bean对象都会直接加载进ioc容器
会按照条件加载进ioc容器@ConditionalXXX会帮助判断按照什么条件进行加载ioc容器

@ConditionalOnClass（name=""）
是判断环境里面叫做XXX的类的字节码文件是否在环境中
如果在环节中就把目标对象加载到ioc容器

@ConditionalMissingBean
是判断是否有本类型或名称的bean如果没有就加载
一定记住这个是判断本bean的类型或名称不存在

@ConditionalOnProperty（name="",havingValue=""）
判断配置文件里面是否存在属性和值是目标的，如果有就注入ioc
 */
