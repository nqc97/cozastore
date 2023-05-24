package com.cybersoft.cozastore.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTHelperUtils {
    // @Value : Giúp lấy key khai báo bên file application.properties
    @Value("${jwt.token.key}")
    String secretKey;

    /**
     * Bước 1 : Tạo key
     * Bước 2 : Sử dụng key mới tạo để sinh ra tokem
     */

    public String generateToken(String data){

//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String key = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println(key);

        // Lấy secretKey đã tạo trước đó để sử dụng
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        // Dùng key để tạo ra token
        String token = Jwts.builder().setSubject(data).signWith(key).compact();

        return token;
    }

    public String validToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
//        Chuẩn bị chìa khóa để tiến hành giải mã
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token) //Truyền token cần giải mã
                .getBody().getSubject();

    }

}

