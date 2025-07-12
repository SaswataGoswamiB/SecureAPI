package com.securityImpl.security.Controller;

import com.securityImpl.security.Repo.Userrepo;
import com.securityImpl.security.Service.UserService;
import com.securityImpl.security.emtities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Userrepo userrepo;

    @Autowired
    private UserService userservice;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        // return userrepo.save(user);

        return  userservice.register(user);
    }

    @PostMapping("/registerall")
    public void registerall(List<User> user){
      user.stream().forEach((x)->{
        userrepo.save(x);
      });
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){

       return  userservice.verifyUser(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public String getadminmessage(){
        return "Welcome to Admin Console!";
    }

}
