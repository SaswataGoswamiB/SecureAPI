package com.securityImpl.security.Service;

import com.securityImpl.security.emtities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
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
        claims.put("Roles", "Comsumer");
        claims.put("mail", "Testing@gmail.com");
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

        String property = env.getProperty("application.jwt.secretkey");
        // Encoding the Secretket into Base64 encoding.
        byte[] decode = Decoders.BASE64.decode(property);
        SecretKey secretKey = Keys.hmacShaKeyFor(decode);
        return secretKey;
    }

    public String extractusername(String jwt) {
       //get the claims  details from the JWt and then get the Subject from it .
        Claims payload = Jwts.
                parser().verifyWith((SecretKey) generateKey()).
                build().parseSignedClaims(jwt).getPayload();

        return payload.getSubject();

//  The way to get Claims value from JWT is to create an object of JwtParser and the send the jwt to
//  parseSignedClaims
//        JwtParserBuilder jwtParserBuilder =
//                Jwts.parser().verifyWith((SecretKey) generateKey());
//        JwtParser build = jwtParserBuilder.build();
// JWS is like a signed JWT Json. JWS stands for JSON web Signature.
//        Jws<Claims> claimsJws = build.parseSignedClaims(jwt);
//        Claims payload = claimsJws.getPayload();
    }


    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        String username = extractusername(jwt);
        String usernamesecond = userDetails.getUsername();
         boolean isTokenExpired = ExpirationCheck(jwt);

         return (username.equals(usernamesecond)) && (!isTokenExpired);
    }

    private boolean ExpirationCheck(String jwt) {

        Claims payload = Jwts.parser().verifyWith((SecretKey) generateKey()).build().
                parseSignedClaims(jwt).getPayload();

        Date expiration = payload.getExpiration();

        // Chedck if the Expiration timke is before today
        boolean valid = expiration.before(new Date());

        return valid;
    }
}