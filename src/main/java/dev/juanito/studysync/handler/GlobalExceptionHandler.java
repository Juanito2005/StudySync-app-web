package dev.juanito.studysync.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.juanito.studysync.exception.EmailAlreadyExistException;
import dev.juanito.studysync.exception.SubjectIdNotFoundException;
import dev.juanito.studysync.exception.SubjectNameAlreadyExistsException;
import dev.juanito.studysync.exception.UserIdNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EmailAlreadyExistException.class)
    // Returns a ResponseEntity because its an object that can contains all (message and http response)
    public ResponseEntity<String> handleEmailAreadyExistException(EmailAlreadyExistException ex) {
        // Return a 409 conlfict and the exception message
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<String> handleUserIdNotFoundException(UserIdNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubjectIdNotFoundException.class)
    public ResponseEntity<String> handleSubjectIdNotFoundException(SubjectIdNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubjectNameAlreadyExistsException.class)
    public ResponseEntity<String> handleSubjectNameAlreadyExistsException(SubjectNameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
