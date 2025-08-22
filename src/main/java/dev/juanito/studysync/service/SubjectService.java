package dev.juanito.studysync.service;

import dev.juanito.studysync.dto.SubjectCreationDto;
import dev.juanito.studysync.dto.SubjectUpdateDto;
import dev.juanito.studysync.model.Subject;

public interface SubjectService {

    //The User's id is already specified in the JWT security context
    public Subject createSubject(SubjectCreationDto subjectCreationDto);

    public Subject findSubjectById(Long id);

    public void deleteSubjectById(Long id);

    public Subject updateSubjectById(SubjectUpdateDto subjectUpdateDto, Long id);
}
