package dev.juanito.studysync.service.impl;

import dev.juanito.studysync.dto.SubjectCreationDto;
import dev.juanito.studysync.dto.SubjectUpdateDto;
import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.service.SubjectService;

public class SubjectServiceImpl implements SubjectService{

    @Override
    public Subject createSubject(SubjectCreationDto subjectCreationDto) {
        throw new UnsupportedOperationException("Unimplemented method 'createSubject'");
    }

    @Override
    public Subject findSubjectById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'findSubjectById'");
    }

    @Override
    public void deleteSubjectById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteSubjectById'");
    }

    @Override
    public Subject updateSubjectById(SubjectUpdateDto subjectUpdateDto, Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'updateSubjectById'");
    }
    
}
