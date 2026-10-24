package dev.juanito.studysync.service;

import dev.juanito.studysync.dto.SubjectCreationDto;
import dev.juanito.studysync.dto.SubjectResponseDto;
import dev.juanito.studysync.dto.SubjectUpdateDto;
import java.util.List;

public interface SubjectService {
    //The User's id is already specified in the JWT security context
    public SubjectResponseDto createSubject(
        SubjectCreationDto subjectCreationDto
    );

    public SubjectResponseDto findSubjectById(Long id);

    public List<SubjectResponseDto> findSubjectsByUser();

    public void deleteSubjectById(Long id);

    public SubjectResponseDto updateSubjectById(
        SubjectUpdateDto subjectUpdateDto,
        Long id
    );
}
