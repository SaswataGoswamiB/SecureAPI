package com.securityImpl.security.Config;

import com.securityImpl.security.Service.CustomUserService;
import com.securityImpl.security.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWtAuthFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */

    private final JWTService jwtservice;

    private final CustomUserService customuserservice;

    public JWtAuthFilter(JWTService jwtservice, CustomUserService customuserservice) {
        this.jwtservice = jwtservice;
        this.customuserservice = customuserservice;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get the auth string
        String authheader = request.getHeader("Authorization");

        //call the UsernamePassowrdFilter if the auth is null or is not a bearer token.
        if(authheader == null || !authheader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return ;
        }

         final String jwt = authheader.substring(7);
         // Now you  have the username.
         final String username = jwtservice.extractusername(jwt);

         //getting auth object from the Secuirty Context
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         if(username!=null && authentication == null){
             //Auth Logic
            UserDetails userDetails = customuserservice.loadUserByUsername(username);
            if(jwtservice.isTokenValid(jwt, userDetails)){
                // Create an authetication object and store it in secuirty context.
                // UsernamePasswordAuthenticationToken is  an implementation of AUTHENTICATION object

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails,
                                            null,userDetails.getAuthorities());

                // Set extra data to the Authentication object like IP-ADDRESS,WHO LOGGED IN,AT WHAT TIME
                //Gives you some extra secuirty.
                usernamePasswordAuthenticationToken.
                        setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);

            }
         }

             filterChain.doFilter(request, response);

    }
}
