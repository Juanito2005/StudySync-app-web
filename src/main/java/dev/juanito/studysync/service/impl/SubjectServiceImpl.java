package dev.juanito.studysync.service.impl;

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

    @Override
    public Subject createSubject(SubjectCreationDto subjectCreationDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getUserId())
            .orElseThrow(() -> new UserIdNotFoundException("The user Id has not been found"));
        checkIfSubjectNameExists(subjectCreationDto.getName());
        Subject subject = new Subject();
        subject.setName(subjectCreationDto.getName());
        subject.setColorHEX(subjectCreationDto.getColor());
        subject.setUser(user);
        return subjectRepository.save(subject);
    }

    
    public void checkIfSubjectNameExists(String name) {
        if (subjectRepository.findByName(name).isPresent()) {
            throw new SubjectNameAlreadyExistsException("Cannot be created a subject with an existing subject name");
        }
    }
}

// @Override
// public Subject findSubjectById(Long id) {
//     return subjectRepository.findById(id).orElseThrow(() -> new SubjectIdNotFoundException("The subject has not been found"));
// }

// @Override
// public void deleteSubjectById(Long id) {
//     Subject subject = findSubjectById(id);
//     subjectRepository.delete(subject);
// }

// @Override
// public Subject updateSubjectById(SubjectUpdateDto subjectUpdateDto, Long id) {
//     Subject subject = findSubjectById(id);
//     if (subjectUpdateDto.getName() != null) {
//         checkIfSubjectNameExists(subjectUpdateDto.getName());
//         subject.setName(subjectUpdateDto.getName());
//     }
//     if (subjectUpdateDto.getColor() != null) {
//         subject.setColorHEX(subjectUpdateDto.getColor());
//     }
//     return subjectRepository.save(subject);
// }