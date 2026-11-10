package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private EmpService loginService;

/**
 * 处理登录请求的POST接口方法
 * @param emp 包含用户登录信息的请求体，会被自动封装为Emp对象
 * @return 返回一个Result对象，包含登录成功或失败的信息
 */
    @PostMapping
    public Result login(@RequestBody Emp emp){

    // 调用loginService的login方法进行登录验证
        Emp e= loginService.login(emp);
    // 判断登录结果是否为空
        if(e!=null) {

            // 创建一个Map集合，用于存放生成JWT令牌所需的自定义声明（用户信息）
            Map<String, Object> map = new HashMap<>();
            // 将登录成功的用户姓名添加到Map中
            map.put("name", e.getName());
            // 将登录成功的用户ID添加到Map中
            map.put("id", e.getId());

            map.put("username",e.getUsername());
            // 调用JwtUtils工具类，根据包含用户信息的Map生成JWT令牌
            // 返回登录成功的响应结果
            String jwt = JwtUtils.generateJwt(map);
            return Result.success(jwt);
        } else{
        // 如果登录失败，返回失败信息和相应提示
            return Result.error("登录失败");
        }
    }
}
