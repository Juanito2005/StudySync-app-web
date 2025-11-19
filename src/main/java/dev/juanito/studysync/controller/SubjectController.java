package dev.juanito.studysync.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.juanito.studysync.dto.SubjectCreationDto;
import dev.juanito.studysync.dto.SubjectUpdateDto;
import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.service.SubjectService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// Porque debe ser @RestController?
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody SubjectCreationDto subjectCreationDto) {
        Subject newSubject = subjectService.createSubject(subjectCreationDto);
        return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
    }
    // Recuerdame porque es @PathVariable instead @PathParam
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable Long id) {
        Subject subject = subjectService.findSubjectById(id);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@Valid @RequestBody SubjectUpdateDto subjectUpdateDto, @PathVariable Long id) {
        Subject subjectUpdated = subjectService.updateSubjectById(subjectUpdateDto, id);
        return new ResponseEntity<>(subjectUpdated, HttpStatus.OK);
    }
}
