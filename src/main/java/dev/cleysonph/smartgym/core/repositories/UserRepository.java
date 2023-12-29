package dev.cleysonph.smartgym.core.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cleysonph.smartgym.core.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    
}
