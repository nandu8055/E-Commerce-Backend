package org.vinchenzo.ecommerce.exception;

public class NoActiveUserException extends RuntimeException {
    public NoActiveUserException(String message) {
        super(message);
    }
}
