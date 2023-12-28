package dev.cleysonph.smartgym.core.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cleysonph.smartgym.core.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
}
