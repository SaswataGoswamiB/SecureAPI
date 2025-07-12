package com.securityImpl.security.Service;

import com.securityImpl.security.emtities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTService {
    @Autowired
    private Environment env;

    @Autowired
    CustomUserService customUserService;

    private Environment env1;

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<String, Object>();

        final UserDetails userDetails = customUserService.loadUserByUsername(user.getUserName());

        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));


        claims.put("mail", "Testing@gmail.com");
        return Jwts.builder().
                claims()
                .add(claims)
                .subject(user.getUserName()).
                issuer("Beni").
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000)).
                and().
                signWith(generateKey()).
                compact();
    }

    private Key generateKey() {

        String property = env.getProperty("application.jwt.secretkey");
        final byte[] decode = Decoders.BASE64.decode(property);
        final SecretKey secretKey = Keys.hmacShaKeyFor(decode);
        return secretKey;
    }

    public String extractusername(String jwt) {
       //get the claims  details from the JWt and then get the Subject from it .


      return   Jwts.parser().verifyWith((SecretKey) generateKey()).
                build().parseSignedClaims(jwt).getPayload().getSubject();

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

        // Check if the Expiration time is before today
        //Test if the Date is before the specified Date.

        boolean valid = expiration.before(new Date());

        return valid;
    }
}