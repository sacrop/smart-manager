package com.useradmin.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.useradmin.management.model.UserDtls;
import com.useradmin.management.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserRepository userrepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDtls createUser(UserDtls user) {
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        return userrepo.save(user);
    }

    @Override
    public boolean checkbyemail(String email) {
        
        return userrepo.existsByemail(email);
    }
    
}
