package dev.juanito.studysync.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.juanito.studysync.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // It must be Optional cause the field could be empty in the db
    Optional<User> findByEmail(String email);
}
