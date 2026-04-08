package com.example.library_management_system.repository;

import com.example.library_management_system.model.LibraryUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<LibraryUser, Long> {
    LibraryUser findByEmail(String email);
}
