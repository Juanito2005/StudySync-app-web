package dev.juanito.studysync.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.model.User;
import java.util.List;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findByName(String name);
    Optional<Subject> findByNameAndUser(String name, User user);
    List<Subject> findByUser(User user);
    Optional<Subject> findByIdAndUser(Long id, User user);
}
