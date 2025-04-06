package com.securityImpl.security.Repo;

import com.securityImpl.security.emtities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Userrepo extends JpaRepository<User,Integer> {
    User findByuserName(String userName);
}
