package dev.juanito.studysync.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.juanito.studysync.dto.RoutineLogCreationDto;
import dev.juanito.studysync.dto.RoutineLogResponseDto;
import dev.juanito.studysync.dto.RoutineLogUpdateDto;
import dev.juanito.studysync.exception.RoutineLogNotFoundException;
import dev.juanito.studysync.exception.SubjectIdNotFoundException;
import dev.juanito.studysync.model.RoutineLog;
import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.repository.RoutineLogRepository;
import dev.juanito.studysync.repository.SubjectRepository;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.service.RoutineLogService;

@Service
public class RoutineLogServiceImpl implements RoutineLogService {

    private final RoutineLogRepository routineLogRepository;
    private final SubjectRepository subjectRepository;
    private final AuthenticationHelper authenticationHelper;

    public RoutineLogServiceImpl(RoutineLogRepository routineLogRepository,
                                  SubjectRepository subjectRepository,
                                  AuthenticationHelper authenticationHelper) {
        this.routineLogRepository = routineLogRepository;
        this.subjectRepository = subjectRepository;
        this.authenticationHelper = authenticationHelper;
    }

    // Mapea una entidad RoutineLog a su DTO de respuesta
    private RoutineLogResponseDto toResponseDto(RoutineLog routineLog) {
        RoutineLogResponseDto dto = new RoutineLogResponseDto();
        dto.setId(routineLog.getId());
        dto.setActualHours(routineLog.getActualHours());
        dto.setDate(routineLog.getDate());
        dto.setNotes(routineLog.getNotes());
        dto.setUserId(routineLog.getUser().getId());
        dto.setUserName(routineLog.getUser().getName());
        dto.setSubjectId(routineLog.getSubject().getId());
        dto.setSubjectName(routineLog.getSubject().getName());
        if (routineLog.getPlanning() != null) {
            dto.setPlanningId(routineLog.getPlanning().getId());
        }
        return dto;
    }

    @Override
    public RoutineLogResponseDto createRoutineLog(RoutineLogCreationDto routineLogCreationDto) {
        User user = authenticationHelper.getAuthenticatedUser();

        // Verificamos que la materia exista y pertenezca al usuario
        Subject subject = subjectRepository.findByIdAndUser(routineLogCreationDto.getSubjectId(), user)
                .orElseThrow(() -> new SubjectIdNotFoundException(
                        "Esa materia no existe o no es tuya, compa"));

        RoutineLog routineLog = new RoutineLog();
        routineLog.setActualHours(routineLogCreationDto.getActualHours());
        routineLog.setDate(routineLogCreationDto.getDate());
        routineLog.setNotes(routineLogCreationDto.getNotes());
        routineLog.setUser(user);
        routineLog.setSubject(subject);

        RoutineLog savedRoutineLog = routineLogRepository.save(routineLog);
        return toResponseDto(savedRoutineLog);
    }

    @Override
    public RoutineLogResponseDto findRoutineLogById(Long id) {
        User user = authenticationHelper.getAuthenticatedUser();
        RoutineLog routineLog = routineLogRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RoutineLogNotFoundException(
                        "RoutineLog no encontrado o no tienes permiso, parce"));
        return toResponseDto(routineLog);
    }

    @Override
    public List<RoutineLogResponseDto> findRoutineLogsByUser() {
        User user = authenticationHelper.getAuthenticatedUser();
        List<RoutineLog> routineLogs = routineLogRepository.findByUser(user);
        return routineLogs.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoutineLogResponseDto> findRoutineLogsBySubject(Long subjectId) {
        User user = authenticationHelper.getAuthenticatedUser();
        // Verificamos que la materia exista
        if (!subjectRepository.existsById(subjectId)) {
            throw new SubjectIdNotFoundException("Esa materia no existe, ¿de dónde la sacaste?");
        }
        List<RoutineLog> routineLogs = routineLogRepository.findByUserAndSubjectId(user, subjectId);
        return routineLogs.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoutineLogResponseDto updateRoutineLog(Long id, RoutineLogUpdateDto routineLogUpdateDto) {
        User user = authenticationHelper.getAuthenticatedUser();

        RoutineLog routineLog = routineLogRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RoutineLogNotFoundException(
                        "RoutineLog no encontrado o no tienes permiso para actualizarlo"));

        if (routineLogUpdateDto.getActualHours() != null) {
            routineLog.setActualHours(routineLogUpdateDto.getActualHours());
        }

        if (routineLogUpdateDto.getDate() != null) {
            routineLog.setDate(routineLogUpdateDto.getDate());
        }

        if (routineLogUpdateDto.getNotes() != null) {
            routineLog.setNotes(routineLogUpdateDto.getNotes());
        }

        if (routineLogUpdateDto.getSubjectId() != null) {
            Subject subject = subjectRepository.findByIdAndUser(routineLogUpdateDto.getSubjectId(), user)
                    .orElseThrow(() -> new SubjectIdNotFoundException(
                            "Esa materia no existe o no es tuya"));
            routineLog.setSubject(subject);
        }

        RoutineLog updatedRoutineLog = routineLogRepository.save(routineLog);
        return toResponseDto(updatedRoutineLog);
    }

    @Override
    public void deleteRoutineLog(Long id) {
        User user = authenticationHelper.getAuthenticatedUser();
        RoutineLog routineLog = routineLogRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RoutineLogNotFoundException(
                        "RoutineLog no encontrado o no tienes permiso para eliminarlo"));
        routineLogRepository.delete(routineLog);
    }
}
