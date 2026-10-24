package dev.juanito.studysync.exception;

public class EmailCannotBeEmptyException extends RuntimeException {

    // A constructor is createad, this returns the message in the service
    public EmailCannotBeEmptyException(String message) {
        // This is a method of RuntimeException class
        super(message);
    }
}
