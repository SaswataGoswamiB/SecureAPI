package com.securityImpl.security.Service.Service.CustomUser;

import com.securityImpl.security.Repo.Userrepo;
import com.securityImpl.security.emtities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;

public class CustomServiceDetails implements UserDetailsService {

    @Autowired
    Userrepo userrepo;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User byuserName = userrepo.findByuserName(username);
        if(Objects.isNull(byuserName)){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(byuserName.getUserName(),
                byuserName.getPasssword(),List.of(new SimpleGrantedAuthority("USER")));
    }
}
