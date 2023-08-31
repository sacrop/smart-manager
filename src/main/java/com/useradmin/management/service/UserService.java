package com.useradmin.management.service;

import com.useradmin.management.model.UserDtls;

public interface UserService {
    
    public UserDtls createUser(UserDtls user);
 
    public boolean checkbyemail(String email);

}
