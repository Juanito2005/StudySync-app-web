package dev.juanito.studysync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.juanito.studysync.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
}
