package com.useradmin.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.useradmin.management.model.UserDetails;
import com.useradmin.management.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserRepository userrepo;

    @Override
    public UserDetails createUser(UserDetails user) {
        
        return userrepo.save(user);
    }

    @Override
    public boolean checkbyemail(String email) {
        
        return userrepo.existsByemail(email);
    }
    
}
