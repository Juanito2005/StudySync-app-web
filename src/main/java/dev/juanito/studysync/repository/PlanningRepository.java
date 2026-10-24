package dev.juanito.studysync.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.juanito.studysync.model.Planning;
import dev.juanito.studysync.model.User;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {

    List<Planning> findByUser(User user);

    List<Planning> findByUserAndSubjectId(User user, Long subjectId);

    Optional<Planning> findByIdAndUser(Long id, User user);
}
