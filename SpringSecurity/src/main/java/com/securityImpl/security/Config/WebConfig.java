package com.securityImpl.security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((request)->{
            request.
                    requestMatchers("user/register","user/login").permitAll().
                    anyRequest().authenticated();
        })
                .csrf((request)->{
                    HttpSecurity disable = request.disable();
                })
                //.formLogin(Customizer.withDefaults())
                 // The httpBAic(Customizer.default)
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userdetailsservice()
    {
        UserDetails user1 = User.builder().
                username("user1").password("{noop}user1password").roles("user").
                build();

        UserDetails user2 = User.builder().
                username("user2").password("{noop}user2password").roles("user").
                build();

        return new InMemoryUserDetailsManager(user1,user2);
    }



}
