package dev.juanito.studysync.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;

import dev.juanito.studysync.dto.SubjectCreationDto;
import dev.juanito.studysync.dto.SubjectResponseDto;
import dev.juanito.studysync.dto.SubjectUpdateDto;
import dev.juanito.studysync.exception.SubjectIdNotFoundException;
import dev.juanito.studysync.exception.SubjectNameAlreadyExistsException;
import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.repository.SubjectRepository;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;
    private final AuthenticationHelper authenticationHelper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, AuthenticationHelper authenticationHelper) {
        this.subjectRepository = subjectRepository;
        this.authenticationHelper = authenticationHelper;
    }

    private SubjectResponseDto toResponseDto(Subject subject) {
        SubjectResponseDto dto = new SubjectResponseDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setColorHEX(subject.getColorHEX());
        dto.setUserId(subject.getUser().getId());
        dto.setUserName(subject.getUser().getName());
        return dto;
    }

    @Override
    public SubjectResponseDto createSubject(SubjectCreationDto subjectCreationDto) {
        User user = authenticationHelper.getAuthenticatedUser();
        if (subjectRepository.findByNameAndUser(subjectCreationDto.getName(), user).isPresent()) {
            throw new SubjectNameAlreadyExistsException("You already have a subject with this name");
        }
        Subject subject = new Subject();
        subject.setName(subjectCreationDto.getName());
        subject.setColorHEX(subjectCreationDto.getColor());
        subject.setUser(user);
        Subject savedSubject = subjectRepository.save(subject);
        return toResponseDto(savedSubject);
    }

    @Override
    public SubjectResponseDto findSubjectById(Long id) {
        User user = authenticationHelper.getAuthenticatedUser();
        Subject findedSubject = subjectRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new SubjectIdNotFoundException("Subjectc not found or access denied"));
        return toResponseDto(findedSubject);
    }

    @Override
    public void deleteSubjectById(Long id) {
        User user = authenticationHelper.getAuthenticatedUser();
        // Since an Optional type is returned, the exceptions must been handle
        Subject subject = subjectRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new SubjectIdNotFoundException("Subject not found or access denied"));
        subjectRepository.delete(subject);
    }

    @Override
    public SubjectResponseDto updateSubjectById(SubjectUpdateDto subjectUpdateDto, Long id) {
        User user = authenticationHelper.getAuthenticatedUser();

        Subject subject = subjectRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new SubjectIdNotFoundException("Subject not found or access denied"));

        if (subjectUpdateDto.getName() != null) {
            Optional<Subject> existingSubject = subjectRepository.findByNameAndUser(subjectUpdateDto.getName(), user);
            if (existingSubject.isPresent() && !existingSubject.get().getId().equals(id)) {
                throw new SubjectNameAlreadyExistsException("You already have a subject with this name");
            }
            subject.setName(subjectUpdateDto.getName());
        }

        if (subjectUpdateDto.getColor() != null) {
            subject.setColorHEX(subjectUpdateDto.getColor());
        }

        Subject updatedSubject = subjectRepository.save(subject);
        return toResponseDto(updatedSubject);
    }
}
