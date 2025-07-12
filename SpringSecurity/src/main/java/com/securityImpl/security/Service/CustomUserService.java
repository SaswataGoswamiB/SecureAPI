package com.securityImpl.security.Service;

import com.securityImpl.security.Repo.Userrepo;
import com.securityImpl.security.Service.Service.CustomUser.CustomUserDetails;
import com.securityImpl.security.emtities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

        if(isadmin()){
            return new
                    org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPasssword(), List.of(new SimpleGrantedAuthority("Admin")));
        }

        return new
                org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPasssword(), List.of(new SimpleGrantedAuthority("User")));

        //return new CustomUserDetails(user);
    }

    private boolean isadmin() {
        return true;
    }
}
