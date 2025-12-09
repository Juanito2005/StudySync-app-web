package dev.juanito.studysync.exception;

public class UserIdNotFoundException extends RuntimeException{

    public UserIdNotFoundException(String message) {
        super(message);
    }
}
