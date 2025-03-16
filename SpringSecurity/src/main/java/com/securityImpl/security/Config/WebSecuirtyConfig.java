//package com.securityImpl.security.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class WebSecuirtyConfig {
//
//    //Defining custom filter chain to take the command
//    //back from Spring security
//    @Bean
//    public SecurityFilterChain secuirityfilterchain(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity.authorizeHttpRequests(request->{
////            request.anyRequest().authenticated();
////        }).httpBasic(Customizer.withDefaults());
//        return httpSecurity.build();
//    }
//}
