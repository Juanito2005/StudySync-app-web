package dev.juanito.studysync.exception;

public class EmailAlreadyExistException  extends RuntimeException{
    // A constructor is createad, this returns the message in the service
    public EmailAlreadyExistException(String message) {
        // This is a method of RuntimeException class
        super(message);
    }
}
