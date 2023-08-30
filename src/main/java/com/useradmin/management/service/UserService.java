package com.useradmin.management.service;

import com.useradmin.management.model.UserDetails;

public interface UserService {
    
    public UserDetails createUser(UserDetails user);
 
    public boolean checkbyemail(String email);

}
