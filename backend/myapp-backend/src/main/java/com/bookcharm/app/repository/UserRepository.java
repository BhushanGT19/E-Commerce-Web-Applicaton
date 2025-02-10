package com.bookcharm.app.repository;

import com.bookcharm.app.model.Address;
import com.bookcharm.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // find user using email
    Optional<User> findByEmail(String email);


	

	
}
