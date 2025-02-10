package com.bookcharm.app.repository;

import com.bookcharm.app.model.Admin;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {

	Optional<Admin> findByUserName(String userName);
    // Additional admin-specific query methods if needed
}
