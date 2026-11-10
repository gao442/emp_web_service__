package com.itheima;

import com.google.gson.Gson;
import com.itheima.controller.DeptController;
import com.itheima.pojo.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
//从测试类所在的包开始，一级一级往上层包找
class Demoproject01ApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Gson gson;

//    @Test
//   public void testJwts(){
//        Map<String,Object> map = new HashMap<>();
//        map.put("id","1");
//        map.put("name","张三");
//
//        String jwt = Jwts.builder()
//                .addClaims(map)
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
//                .signWith(SignatureAlgorithm.HS256, "123456")
//                .compact();
//        System.out.println(jwt);
//    }
//    @Test
//    public void parseJtws(){
//        //解析jwt
//        Claims claims=Jwts.parser()
//                .setSigningKey("123456")
//                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOiIxIiwiZXhwIjoxNzk0MTI0NDY2fQ.NnaKZ2ZeIGyhByILaUVvg82vci-PYixVRg9UQ19EmEY")
//                .getBody();
//        System.out.println(claims);
//    }
    /*
    三要素不能少分别是签名算法和签名，自定义内容，数字签名三部分
    签名算法：Hs256
    签名：123456
    自定义内容：id,name
    过期时间：1天

    除了数字签名前面的部分都是经过base64编码的
     */

    @Test
    public void testGetBean(){



        //根据名称获取bean对象
        DeptController bean1 = (DeptController) applicationContext.getBean("deptController");
        System.out.println(bean1);

        //根据bean类型获取
        DeptController bean2 = applicationContext.getBean(DeptController.class);
        System.out.println(bean2);

        //根据名字和类型获取
        DeptController bean3 = applicationContext.getBean("deptController", DeptController.class);
        System.out.println(bean3);
        /*
        地址一致说明只有一个bean对象说明这是单例的
         */

        System.out.println(gson.toJson(Result.success()));
    }



}
//prototype每次使用该bean时都会创建一个实例对象
//sigleton：ioc容器内部只有一个bean对象（单例）（默认的）
//@Scope（）可以改变作用域（使得bean对象再每次使用时都会生成）

/*
在类内部写入@Laxy可以使得bean对象延迟创建，延迟到第一次使用这个bean对象的时候
默认是在容器创建时对所有的bean对象进行初始化，lazy可以延迟初始化

 */

/*
第三方bean的管理：（第三方依赖中的bean对象）
在类上发加上conponent注解可以让spring管理这个类，放到ioc容器里面
@Bean可以声明这还是个第三方的bean，在配置类里面里面进行定义

@Bean
public class SAXReader{
    public SAXReader saxReader(){
        return new SAXReader();
    }

}


 */



