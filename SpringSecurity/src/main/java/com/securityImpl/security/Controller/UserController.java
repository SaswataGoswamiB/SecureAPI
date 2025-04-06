package com.securityImpl.security.Controller;

import com.securityImpl.security.Repo.Userrepo;
import com.securityImpl.security.emtities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Userrepo userrepo;

    @PostMapping("/register")
    public User register(@RequestBody User user){
         return userrepo.save(user);
    }

    @PostMapping("/registerall")
    public void registerall(List<User> user){
      user.stream().forEach((x)->{
        userrepo.save(x);
      });
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){
         if(Objects.nonNull(userrepo.findByuserName(user.getUserName()))){
                return "Logged in";
         }

         return "Please Register yourself";
    }
}
