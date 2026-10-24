package dev.juanito.studysync.service;

import java.util.List;

import dev.juanito.studysync.dto.PlanningCreationDto;
import dev.juanito.studysync.dto.PlanningResponseDto;
import dev.juanito.studysync.dto.PlanningUpdateDto;

public interface PlanningService {

    PlanningResponseDto createPlanning(PlanningCreationDto planningCreationDto);

    PlanningResponseDto findPlanningById(Long id);

    List<PlanningResponseDto> findPlanningsByUser();

    List<PlanningResponseDto> findPlanningsBySubject(Long subjectId);

    PlanningResponseDto updatePlanning(Long id, PlanningUpdateDto planningUpdateDto);

    void deletePlanning(Long id);
}
