package org.taskSystem.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskSystem.jwt.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>
{
    Optional<User> findByEmail(String email);
}
