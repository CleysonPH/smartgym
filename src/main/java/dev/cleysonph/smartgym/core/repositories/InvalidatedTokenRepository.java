package dev.cleysonph.smartgym.core.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.cleysonph.smartgym.core.models.InvalidatedToken;

public interface InvalidatedTokenRepository extends CrudRepository<InvalidatedToken, String> {
    
}
