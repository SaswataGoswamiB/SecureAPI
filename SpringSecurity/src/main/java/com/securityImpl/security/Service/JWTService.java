package com.securityImpl.security.Service;

import com.securityImpl.security.emtities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTService {
    @Autowired
    private Environment env;

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<String, Object>();
        return Jwts.builder().
                claims()
                .add(claims)
                .subject(user.getUserName()).
                issuer("Beni").
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000)).
                and().
                signWith(generateKey()).compact();
    }

    private Key generateKey() {
        String secretkey = env.getProperty("application.jwt.secretkey");
        byte[] decode = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(decode);
    }
}