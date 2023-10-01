package com.useradmin.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.useradmin.management.model.UserDtls;
import java.util.List;




public interface UserRepository extends JpaRepository<UserDtls,Integer> {

    public boolean existsByemail(String email);

    public UserDtls findByEmail(String email);
    
    public UserDtls findById(int id);

        @Query("SELECT c FROM UserDtls c WHERE c.name=:name")
        public List<UserDtls> findByName(@Param("name") String name);

        

    
    
}
