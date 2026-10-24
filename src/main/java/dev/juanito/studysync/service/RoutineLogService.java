package dev.juanito.studysync.service;

import java.util.List;

import dev.juanito.studysync.dto.RoutineLogCreationDto;
import dev.juanito.studysync.dto.RoutineLogResponseDto;
import dev.juanito.studysync.dto.RoutineLogUpdateDto;

public interface RoutineLogService {

    RoutineLogResponseDto createRoutineLog(RoutineLogCreationDto routineLogCreationDto);

    RoutineLogResponseDto findRoutineLogById(Long id);

    List<RoutineLogResponseDto> findRoutineLogsByUser();

    List<RoutineLogResponseDto> findRoutineLogsBySubject(Long subjectId);

    RoutineLogResponseDto updateRoutineLog(Long id, RoutineLogUpdateDto routineLogUpdateDto);

    void deleteRoutineLog(Long id);
}
