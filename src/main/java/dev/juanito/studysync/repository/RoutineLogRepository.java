package dev.juanito.studysync.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.juanito.studysync.model.RoutineLog;
import dev.juanito.studysync.model.User;

@Repository
public interface RoutineLogRepository extends JpaRepository<RoutineLog, Long> {

    List<RoutineLog> findByUser(User user);

    List<RoutineLog> findByUserAndSubjectId(User user, Long subjectId);

    Optional<RoutineLog> findByIdAndUser(Long id, User user);
}
