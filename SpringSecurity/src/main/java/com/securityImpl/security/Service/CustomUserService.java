package com.securityImpl.security.Service;

import com.securityImpl.security.Repo.Userrepo;
import com.securityImpl.security.Service.Service.CustomUser.CustomUserDetails;
import com.securityImpl.security.emtities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Component
public class CustomUserService implements UserDetailsService {

    private final Userrepo userrepository;

    @Autowired
    public CustomUserService(Userrepo userrepository) {
        this.userrepository = userrepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userrepository.findByuserName(username);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPasssword(),Collections.singleton(()->"Role"));

        //return new CustomUserDetails(user);
    }
}
