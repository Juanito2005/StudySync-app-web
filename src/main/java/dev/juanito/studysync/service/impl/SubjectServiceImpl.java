package dev.juanito.studysync.service.impl;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.juanito.studysync.dto.SubjectCreationDto;
import dev.juanito.studysync.dto.SubjectUpdateDto;
import dev.juanito.studysync.exception.SubjectIdNotFoundException;
import dev.juanito.studysync.exception.SubjectNameAlreadyExistsException;
import dev.juanito.studysync.exception.UserIdNotFoundException;
import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.repository.SubjectRepository;
import dev.juanito.studysync.repository.UserRepository;
import dev.juanito.studysync.security.UserPrincipal;
import dev.juanito.studysync.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    // A Security Context is created for avoid the mess of being calling the db all the time
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userRepository.findById(userPrincipal.getUserId())
            .orElseThrow(() -> new UserIdNotFoundException("Authenticated user not found"));
    }

    @Override
    public Subject createSubject(SubjectCreationDto subjectCreationDto) {
        User user = getAuthenticatedUser();
        if (subjectRepository.findByNameAndUser(subjectCreationDto.getName(), user).isPresent()) {
            throw new SubjectNameAlreadyExistsException("You already have a subject with this name");
        }
        Subject subject = new Subject();
        subject.setName(subjectCreationDto.getName());
        subject.setColorHEX(subjectCreationDto.getColor());
        subject.setUser(user);
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findSubjectById(Long id) {
        User user = getAuthenticatedUser();
        return subjectRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new SubjectIdNotFoundException("Subjectcnot found or access denied"));
    }

    @Override
    public void deleteSubjectById(Long id) {
        User user = getAuthenticatedUser();
        // Since an Optional type is returned, the exceptions must been handle
        Subject subject = subjectRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new SubjectIdNotFoundException("Subject not found or access denied"));
        subjectRepository.delete(subject);
    }

    @Override
    public Subject updateSubjectById(SubjectUpdateDto subjectUpdateDto, Long id) {
        User user = getAuthenticatedUser();

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

        return subjectRepository.save(subject);
    }
}
