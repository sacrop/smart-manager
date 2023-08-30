package com.useradmin.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.useradmin.management.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails,Integer> {

    public boolean existsByemail(String email);
    
}
