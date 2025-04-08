package com.vitalisalexia.sms_backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretkey="Onz5fS0zKVRu9sfToD4bMUarYBsjOVfntgjQYDN/hik=";

//    public JwtService() {
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
//            System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25----------------------->secretkey : " + secretkey);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25-----------------------> Token username : " + username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25-----------------------> chceking token expiratio***");
        if ( extractExpiration(token).before(new Date()))
            System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25-----------------------token expired");
        System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25-----------------------token not expired" +  extractExpiration(token).before(new Date()));
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateToken(String username) {

        System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25----------------------->Generating token<-------------");
        Map<String, Object> claims = new HashMap<String, Object>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}