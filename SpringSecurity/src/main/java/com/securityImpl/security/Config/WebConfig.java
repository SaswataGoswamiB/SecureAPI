package com.securityImpl.security.Config;

import com.securityImpl.security.Service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Autowired
   private  CustomUserService customuserservice;

    private final JWtAuthFilter jetauthfilter;

    public WebConfig(CustomUserService customuserservice, JWtAuthFilter jetauthfilter) {
        this.customuserservice = customuserservice;
        this.jetauthfilter = jetauthfilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests((req)->{
                req.requestMatchers("/user/login","/user/register").permitAll()
            .anyRequest().authenticated();
            }).csrf(CsrfConfigurer::disable)
                    //sesion disabled as we are implementing JWT
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 🚫 No session
                    )
                    .formLogin(AbstractHttpConfigurer::disable)
                    // Will not be used if using JWT
                    //.httpBasic(Customizer.withDefaults())
                    //here we are telling the Spring to allow the jwtfilter bfore the default Filter whihc is
                    //UsernamePasswordAuthenticationFilter
                    .addFilterBefore(jetauthfilter, UsernamePasswordAuthenticationFilter.class);
          return httpSecurity.build();
    }

    //@Bean
//    public UserDetailsService userdetailsservice()
//    {
//        UserDetails user1 = User.builder().
//                username("user1").password("{noop}user1password").roles("user").
//                build();
//
//        UserDetails user2 = User.builder().
//                username("user2").password("{noop}user2password").roles("user").
//                build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }
// you can use the above one or this one as well.

//@Bean
    //using a @Bean is mandatory here
//@Bean
public UserDetailsService getuserdetails(){

        User.UserBuilder user1 = User.builder().
                username("user1").password("{noop}user1password").roles("user");

        User.UserBuilder user2 = User.builder().
                username("user2").password("{noop}user2password").roles("user");

        return new InMemoryUserDetailsManager(user1.build(),user2.build());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationProvider getauthenticationprovider(){
        DaoAuthenticationProvider authprovder = new DaoAuthenticationProvider();
        authprovder.setUserDetailsService(customuserservice);
        //authprovder.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authprovder.setPasswordEncoder(bCryptPasswordEncoder());
        return authprovder;
    }
    @Bean
    public AuthenticationManager getauthenticationmanager(AuthenticationConfiguration configuration) throws Exception {

            return configuration.getAuthenticationManager();
    }

}
