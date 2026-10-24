package dev.juanito.studysync.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.juanito.studysync.dto.PlanningCreationDto;
import dev.juanito.studysync.dto.PlanningResponseDto;
import dev.juanito.studysync.dto.PlanningUpdateDto;
import dev.juanito.studysync.exception.PlanningNotFoundException;
import dev.juanito.studysync.exception.SubjectIdNotFoundException;
import dev.juanito.studysync.model.Planning;
import dev.juanito.studysync.model.Status;
import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.repository.PlanningRepository;
import dev.juanito.studysync.repository.SubjectRepository;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.service.PlanningService;

@Service
public class PlanningServiceImpl implements PlanningService {

    private final PlanningRepository planningRepository;
    private final SubjectRepository subjectRepository;
    private final AuthenticationHelper authenticationHelper;

    public PlanningServiceImpl(PlanningRepository planningRepository,
                                SubjectRepository subjectRepository,
                                AuthenticationHelper authenticationHelper) {
        this.planningRepository = planningRepository;
        this.subjectRepository = subjectRepository;
        this.authenticationHelper = authenticationHelper;
    }

    // Mapea una entidad Planning a su DTO de respuesta
    private PlanningResponseDto toResponseDto(Planning planning) {
        PlanningResponseDto dto = new PlanningResponseDto();
        dto.setId(planning.getId());
        dto.setPlannedHours(planning.getPlannedHours());
        dto.setDate(planning.getDate());
        dto.setStatus(planning.getStatus() != null ? planning.getStatus().name() : null);
        dto.setUserId(planning.getUser().getId());
        dto.setUserName(planning.getUser().getName());
        dto.setSubjectId(planning.getSubject().getId());
        dto.setSubjectName(planning.getSubject().getName());
        if (planning.getRoutineLog() != null) {
            dto.setRoutineLogId(planning.getRoutineLog().getId());
        }
        return dto;
    }

    @Override
    public PlanningResponseDto createPlanning(PlanningCreationDto planningCreationDto) {
        User user = authenticationHelper.getAuthenticatedUser();

        // Verificamos que la materia exista y pertenezca al usuario
        Subject subject = subjectRepository.findByIdAndUser(planningCreationDto.getSubjectId(), user)
                .orElseThrow(() -> new SubjectIdNotFoundException(
                        "Esa materia no existe o no es tuya, compa"));

        Planning planning = new Planning();
        planning.setPlannedHours(planningCreationDto.getPlannedHours());
        planning.setDate(planningCreationDto.getDate());
        planning.setStatus(Status.CREATED); // Todo planning nace en estado CREATED
        planning.setUser(user);
        planning.setSubject(subject);

        Planning savedPlanning = planningRepository.save(planning);
        return toResponseDto(savedPlanning);
    }

    @Override
    public PlanningResponseDto findPlanningById(Long id) {
        User user = authenticationHelper.getAuthenticatedUser();
        Planning planning = planningRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new PlanningNotFoundException(
                        "Planning no encontrado o no tienes permiso, parce"));
        return toResponseDto(planning);
    }

    @Override
    public List<PlanningResponseDto> findPlanningsByUser() {
        User user = authenticationHelper.getAuthenticatedUser();
        List<Planning> plannings = planningRepository.findByUser(user);
        return plannings.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanningResponseDto> findPlanningsBySubject(Long subjectId) {
        User user = authenticationHelper.getAuthenticatedUser();
        // Verificamos que la materia exista y sea del usuario
        if (!subjectRepository.existsById(subjectId)) {
            throw new SubjectIdNotFoundException("Esa materia no existe, ¿de dónde la sacaste?");
        }
        List<Planning> plannings = planningRepository.findByUserAndSubjectId(user, subjectId);
        return plannings.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlanningResponseDto updatePlanning(Long id, PlanningUpdateDto planningUpdateDto) {
        User user = authenticationHelper.getAuthenticatedUser();

        Planning planning = planningRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new PlanningNotFoundException(
                        "Planning no encontrado o no tienes permiso para actualizarlo"));

        if (planningUpdateDto.getPlannedHours() != null) {
            planning.setPlannedHours(planningUpdateDto.getPlannedHours());
        }

        if (planningUpdateDto.getDate() != null) {
            planning.setDate(planningUpdateDto.getDate());
        }

        if (planningUpdateDto.getStatus() != null) {
            try {
                planning.setStatus(Status.valueOf(planningUpdateDto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Estado inválido, parce. Usa: CREATED, COMPLETED o DELETED");
            }
        }

        if (planningUpdateDto.getSubjectId() != null) {
            Subject subject = subjectRepository.findByIdAndUser(planningUpdateDto.getSubjectId(), user)
                    .orElseThrow(() -> new SubjectIdNotFoundException(
                            "Esa materia no existe o no es tuya"));
            planning.setSubject(subject);
        }

        Planning updatedPlanning = planningRepository.save(planning);
        return toResponseDto(updatedPlanning);
    }

    @Override
    public void deletePlanning(Long id) {
        User user = authenticationHelper.getAuthenticatedUser();
        Planning planning = planningRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new PlanningNotFoundException(
                        "Planning no encontrado o no tienes permiso para eliminarlo"));
        planningRepository.delete(planning);
    }
}
