package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    //生成token
    public static String generateJwt(Map<String,Object> map){

//        String jwt = Jwts.builder()
//                .addClaims(map)
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
//                .signWith(SignatureAlgorithm.HS256, "123456")
//                .compact();
//        return jwt;

        String jwt = Jwts.builder()
                .addClaims(map)  //将用户信息(如id)写入JWT载荷，没有这行解析出来的claims将为空
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
                .signWith(SignatureAlgorithm.HS256, "123456")
                .compact();
        return jwt;
    }

    //解析token
    public static Map<String,Object> parseJwt(String jwt){
//        Claims claims=Jwts.parser()
//                .setSigningKey("123456")
//                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOiIxIiwiZXhwIjoxNzk0MTI0NDY2fQ.NnaKZ2ZeIGyhByILaUVvg82vci-PYixVRg9UQ19EmEY")
//                .getBody();
//        return claims;

        Claims claims=Jwts.parser()
                .setSigningKey("123456")
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
