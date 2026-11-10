/*******************    💫 Codegeex Inline Diff    *******************/
package com.itheima.controller;

import com.itheima.pojo.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    /**
     * 设置Cookie
     * @param response HttpServletResponse对象，用于向客户端发送响应
     * @return 返回操作结果
     */
    @GetMapping("c1")
    public static Result cookie1(HttpServletResponse response){
        // 创建一个名为"login_username"，值为"gaowenbo"的Cookie对象
        // 并将其添加到HTTP响应中，服务器会通过响应头(Set-Cookie)将其发送给客户端浏览器
        response.addCookie(new Cookie("login_username","gao"));
        return Result.success("设置成功");
    }

    /**
     * 获取Cookie
     * @param request HttpServletRequest对象，用于接收客户端的请求
     * @return 返回操作结果
     */
    @GetMapping("c2")
    public static Result cookie2(HttpServletRequest request){

        // 从请求头中获取客户端传递过来的所有Cookie数组
        Cookie[] cookies = request.getCookies();
        // 遍历Cookie数组
        for (Cookie cookie : cookies) {
            // 判断当前Cookie的名称是否为"login_username"
            if(cookie.getName().equals("login_username")){
                // 如果匹配，则打印该Cookie的值到控制台
                System.out.println(cookie.getValue());
            }
        }
        return Result.success();
    }

    //</edited_code>
    //
    //### 原理讲解
    //
    //这段代码的核心原理是**HTTP协议中的Cookie机制**，主要用于在客户端（浏览器）和服务器之间维持状态信息。因为HTTP协议本身是无状态的，服务器默认无法区分两次请求是否来自同一个用户，而Cookie就是解决这一问题的常用方案之一。
    //
    //1. **设置Cookie（`c1`接口原理）**：
    //   当浏览器访问 `/c1` 接口时，服务器端的 `cookie1` 方法被调用。通过 `response.addCookie()`，服务器会在HTTP响应报文中添加一个 `Set-Cookie` 响应头（例如：`Set-Cookie: login_username=gaowenbo`）。浏览器接收到这个响应后，会解析该头部信息，并将这个Cookie保存在本地（内存或硬盘中）。
    //
    //2. **获取Cookie（`c2`接口原理）**：
    //****************  12d9ce7200f24ff986f4685b843c8a0b  ****************/
    /*
    在浏览器里面cookie会存在浏览器本地，每次请求都会带着cookie通过请求头携带到服务端
    移动端无法使用cookie，而且用户可以禁用cookie
    而且cookie 不能跨域（协议，IP，端口）
     */
    @GetMapping("s1")
    public Result session1(HttpSession session){

        session.setAttribute("login_username","gaowenbo");
        return Result.success("设置成功");
    }

    @GetMapping("s2")
    public Result session2(HttpServletRequest request){
        HttpSession session = request.getSession();
        String login_username = (String) session.getAttribute("login_username");
        System.out.println(login_username);
        return Result.success();

    }
    /*
    cookie储存在客户端里面，session储存在服务器里面
    session依赖于cookie，sessionid储存在cookie中
    sessionid是唯一的，每次请求都会携带sessionid，
    服务器根据sessionid找到对应的session对象
    seesion 是通过cookie来传递seesionid的

    服务器集群无法使用session
     */
}

