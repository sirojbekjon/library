package com.example.library.security;

import com.example.library.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final long expireTime = 1000*60*60*24;
    private static final String secretkey ="MAXFIYSOZHECHKIMBILMASIN";

    public String generateToken(String  username, Role role){
        Date date = new Date(System.currentTimeMillis() + expireTime);
        String token =
                Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(date)
                .claim("roles",role.getName())
                .signWith(SignatureAlgorithm.HS512,secretkey)
                .compact();
        return token;

    }

    public String getUserNameFromToken(String token){
        try{
            String username = Jwts
                    .parser()
                    .setSigningKey(secretkey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;

        }catch (Exception e){
            return null;
        }
    }
}
