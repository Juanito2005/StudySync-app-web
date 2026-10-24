package dev.juanito.studysync.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.juanito.studysync.dto.RoutineLogCreationDto;
import dev.juanito.studysync.dto.RoutineLogResponseDto;
import dev.juanito.studysync.dto.RoutineLogUpdateDto;
import dev.juanito.studysync.service.RoutineLogService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/routine-logs")
public class RoutineLogController {

    private final RoutineLogService routineLogService;

    public RoutineLogController(RoutineLogService routineLogService) {
        this.routineLogService = routineLogService;
    }

    @PostMapping
    public ResponseEntity<RoutineLogResponseDto> createRoutineLog(
            @Valid @RequestBody RoutineLogCreationDto routineLogCreationDto) {
        RoutineLogResponseDto newRoutineLog = routineLogService.createRoutineLog(routineLogCreationDto);
        return new ResponseEntity<>(newRoutineLog, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoutineLogResponseDto> getRoutineLog(@PathVariable Long id) {
        RoutineLogResponseDto routineLog = routineLogService.findRoutineLogById(id);
        return new ResponseEntity<>(routineLog, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoutineLogResponseDto>> getRoutineLogs(
            @RequestParam(required = false) Long subjectId) {
        List<RoutineLogResponseDto> routineLogs;
        if (subjectId != null) {
            // Filtrar por materia
            routineLogs = routineLogService.findRoutineLogsBySubject(subjectId);
        } else {
            // Devolver todos los routine logs del usuario
            routineLogs = routineLogService.findRoutineLogsByUser();
        }
        return new ResponseEntity<>(routineLogs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoutineLogResponseDto> updateRoutineLog(
            @Valid @RequestBody RoutineLogUpdateDto routineLogUpdateDto,
            @PathVariable Long id) {
        RoutineLogResponseDto updatedRoutineLog = routineLogService.updateRoutineLog(id, routineLogUpdateDto);
        return new ResponseEntity<>(updatedRoutineLog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutineLog(@PathVariable Long id) {
        routineLogService.deleteRoutineLog(id);
        return ResponseEntity.noContent().build();
    }
}
