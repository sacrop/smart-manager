package com.useradmin.management.service;

import java.util.List;

import com.useradmin.management.model.UserDtls;

public interface UserService {
    
    public UserDtls createUser(UserDtls user);
 
    public boolean checkbyemail(String email);

    public void deleteUserById(int id);

    public void changeUserRole(int id,String newRole);

    public List<UserDtls> getAllUser();

    public List<UserDtls> getUserByName(String name);

    public UserDtls getUserByEmail(String email);

    public UserDtls getUserById(int id);


}
