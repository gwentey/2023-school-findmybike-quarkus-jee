package fr.pantheonsorbonne.ufr27.miage.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class ChargeValidationNotSentException extends RuntimeException {

    public ChargeValidationNotSentException(String message, Throwable cause) {
        super(message, cause);
    }
}
