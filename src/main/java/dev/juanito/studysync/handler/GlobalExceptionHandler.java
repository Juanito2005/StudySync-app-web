package dev.juanito.studysync.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.juanito.studysync.exception.EmailAlreadyExistException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<String> handleEmailAreadyExistException(EmailAlreadyExistException ex) {
        // Return a 409 conlfict and the exception message
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Metodo pal futuro para updateUser
    // @ExceptionHandler(UserNotFoundException.class)
    // public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
    //     return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    // }
}
