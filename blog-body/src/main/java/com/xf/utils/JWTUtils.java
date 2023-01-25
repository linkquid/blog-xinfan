package com.xf.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : JWT工具类
 */
public class JWTUtils {
    //  秘钥
    private static final String jwtToken = "12345Mszlu!@#$$";

    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)   //  签发算法、秘钥
                .setClaims(claims)  //  body数据
                .setIssuedAt(new Date())    //  签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60));  //  过期时间1小时
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
