package com.securityImpl.security.Service;

import com.securityImpl.security.Repo.Userrepo;
import com.securityImpl.security.emtities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Userrepo userrrepo;

    private final BCryptPasswordEncoder bcryptencoder;

    public UserService(Userrepo userrrepo, BCryptPasswordEncoder bcryptencoder) {
        this.userrrepo = userrrepo;
        this.bcryptencoder = bcryptencoder;
    }


    public User register(User user) {
        user.setPasssword(bcryptencoder.encode(user.getPasssword()));
        return userrrepo.save(user);
    }


}
