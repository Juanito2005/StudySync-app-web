package dev.juanito.studysync.handler;

import dev.juanito.studysync.exception.EmailAlreadyExistException;
import dev.juanito.studysync.exception.EmailCannotBeEmptyException;
import dev.juanito.studysync.exception.PlanningNotFoundException;
import dev.juanito.studysync.exception.RoutineLogNotFoundException;
import dev.juanito.studysync.exception.SubjectIdNotFoundException;
import dev.juanito.studysync.exception.SubjectNameAlreadyExistsException;
import dev.juanito.studysync.exception.UserEmailNotFoundException;
import dev.juanito.studysync.exception.UserIdNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<String> handleEmailAreadyExistException(
        EmailAlreadyExistException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailCannotBeEmptyException.class)
    public ResponseEntity<String> handleEmailCannotBeEmptyException(
        EmailCannotBeEmptyException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<String> handleUserIdNotFoundException(
        UserIdNotFoundException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubjectIdNotFoundException.class)
    public ResponseEntity<String> handleSubjectIdNotFoundException(
        SubjectIdNotFoundException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubjectNameAlreadyExistsException.class)
    public ResponseEntity<String> handleSubjectNameAlreadyExistsException(
        SubjectNameAlreadyExistsException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<String> handleUserEmailNotFoundException(
        UserEmailNotFoundException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlanningNotFoundException.class)
    public ResponseEntity<String> handlePlanningNotFoundException(
        PlanningNotFoundException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoutineLogNotFoundException.class)
    public ResponseEntity<String> handleRoutineLogNotFoundException(
        RoutineLogNotFoundException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
        IllegalArgumentException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Cuando intentas borrar una materia que tiene planes o sesiones asociados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(
        DataIntegrityViolationException ex
    ) {
        return new ResponseEntity<>(
            "No se puede eliminar porque tiene planes o sesiones asociados. Borra primero los planes y sesiones de esta materia.",
            HttpStatus.CONFLICT
        );
    }
}
