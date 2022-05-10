package com.swissre.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception for ProcessCreation execution
 */
public class ProcessCreationException extends ResponseStatusException {
    public ProcessCreationException(String message){
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}
