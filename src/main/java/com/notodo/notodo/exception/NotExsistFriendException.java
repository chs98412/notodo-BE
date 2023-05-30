package com.notodo.notodo.exception;

import org.springframework.http.ResponseEntity;

public class NotExsistFriendException extends RuntimeException {
    public NotExsistFriendException(String message) {
        super(message);
    }
}
