package com.useradmin.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.useradmin.management.model.UserDtls;



public interface UserRepository extends JpaRepository<UserDtls,Integer> {

    public boolean existsByemail(String email);

    public UserDtls findByEmail(String email);
    
}
