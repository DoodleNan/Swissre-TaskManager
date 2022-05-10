package com.swissre.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception for ProcessKiller execution
 */
public class ProcessKillerException extends ResponseStatusException {
    public ProcessKillerException(String message){
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }

}
