package com.useradmin.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.useradmin.management.configuration.CustomUsrDtls;
import com.useradmin.management.model.UserDtls;
import com.useradmin.management.repository.UserRepository;

@Service
public class UserdtlsService implements UserDetailsService {

    @Autowired
    private UserRepository userrepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        UserDtls user= userrepo.findByEmail(email);

        if(user!=null){
            return new CustomUsrDtls(user);
        }
        throw new UsernameNotFoundException("user not available");

    }
    
}
