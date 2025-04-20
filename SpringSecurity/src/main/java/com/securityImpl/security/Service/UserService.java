package com.securityImpl.security.Service;

import com.securityImpl.security.Repo.Userrepo;
import com.securityImpl.security.emtities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final Userrepo userrrepo;

    private final BCryptPasswordEncoder bcryptencoder;

    private AuthenticationManager authmanager;

    public UserService(Userrepo userrrepo, BCryptPasswordEncoder bcryptencoder, AuthenticationManager authmanager) {
        this.userrrepo = userrrepo;
        this.bcryptencoder = bcryptencoder;
        this.authmanager = authmanager;
    }


    public User register(User user) {
        user.setPasssword(bcryptencoder.encode(user.getPasssword()));
        return userrrepo.save(user);
    }

    public String verifyUser(User user) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPasssword());
        Authentication authresult = authmanager.authenticate(authentication);

    if(Objects.nonNull(userrrepo.findByuserName(user.getUserName()))){
        return "Logged in";
    }
         return "Please Register yourself";
    }

//    public String verifyUser(User user) {
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPasssword());
//        Authentication authresult = authmanager.authenticate(authentication);
//
//        if (authresult.isAuthenticated()) {
//            return "Logged in";
//        }
//
//        return "Please Register yourself!!";
//    }

}
