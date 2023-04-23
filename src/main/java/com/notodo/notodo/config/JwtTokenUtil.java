package com.notodo.notodo.config;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenUtil {
    private String secretKey = "token-secret-keyasbsdasdasdyjgiuhgiuhiasd";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public String generateToken(String platform,String uid, String nickname,String thumbnail) {
        long tokenPeriod = 1000L * 60L * 10L;
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("platform", platform);
        claims.put("nickname", nickname);

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("test") // 토큰발급자(iss)
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(new Date(System.currentTimeMillis()+60*10000*60)) // 만료시간(exp)
                .setSubject(uid) //  토큰 제목(subject)
                .claim("nickname", nickname)
                .claim("uid", uid)
                .claim("thumbnail", thumbnail)
                .claim("platform", platform)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes())) // 알고리즘, 시크릿 키
                .compact();
    }


    public boolean verifyToken(String token) {
        System.out.println("verify");
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }
}
