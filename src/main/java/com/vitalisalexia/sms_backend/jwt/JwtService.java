package com.vitalisalexia.sms_backend.jwt;

import com.vitalisalexia.sms_backend.user.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {


    private String secretkey="";

    public JwtService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String generateToken(String username) {

        System.out.println("---------------->Generating token<-------------");
        Map<String, Object> claims = new HashMap<String, Object>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()  + 60 * 60 * 10))
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}