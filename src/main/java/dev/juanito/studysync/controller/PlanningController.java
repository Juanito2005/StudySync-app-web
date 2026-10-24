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

import dev.juanito.studysync.dto.PlanningCreationDto;
import dev.juanito.studysync.dto.PlanningResponseDto;
import dev.juanito.studysync.dto.PlanningUpdateDto;
import dev.juanito.studysync.service.PlanningService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plannings")
public class PlanningController {

    private final PlanningService planningService;

    public PlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @PostMapping
    public ResponseEntity<PlanningResponseDto> createPlanning(
            @Valid @RequestBody PlanningCreationDto planningCreationDto) {
        PlanningResponseDto newPlanning = planningService.createPlanning(planningCreationDto);
        return new ResponseEntity<>(newPlanning, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanningResponseDto> getPlanning(@PathVariable Long id) {
        PlanningResponseDto planning = planningService.findPlanningById(id);
        return new ResponseEntity<>(planning, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PlanningResponseDto>> getPlannings(
            @RequestParam(required = false) Long subjectId) {
        List<PlanningResponseDto> plannings;
        if (subjectId != null) {
            // Filtrar por materia
            plannings = planningService.findPlanningsBySubject(subjectId);
        } else {
            // Devolver todos los plannings del usuario
            plannings = planningService.findPlanningsByUser();
        }
        return new ResponseEntity<>(plannings, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanningResponseDto> updatePlanning(
            @Valid @RequestBody PlanningUpdateDto planningUpdateDto,
            @PathVariable Long id) {
        PlanningResponseDto updatedPlanning = planningService.updatePlanning(id, planningUpdateDto);
        return new ResponseEntity<>(updatedPlanning, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id) {
        planningService.deletePlanning(id);
        return ResponseEntity.noContent().build();
    }
}
