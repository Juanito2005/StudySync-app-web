package dev.juanito.studysync.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.juanito.studysync.exception.EmailAlreadyExistException;
import dev.juanito.studysync.exception.InvalidFieldsException;
import dev.juanito.studysync.exception.UserIdNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmailAlreadyExistException.class)
    // Returns a ResponseEntity because its an object that can contains all (message and http response)
    public ResponseEntity<String> handleEmailAreadyExistException(EmailAlreadyExistException ex) {
        // Return a 409 conlfict and the exception message
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // It also can be used for the update exception method, isn't this G?
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<String> handleUserIdNotFoundException(UserIdNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<String> handleInvalidFieldsException(InvalidFieldsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
