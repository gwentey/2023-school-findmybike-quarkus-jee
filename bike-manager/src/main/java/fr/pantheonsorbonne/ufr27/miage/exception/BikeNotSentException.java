package fr.pantheonsorbonne.ufr27.miage.exception;

import java.io.IOException;

public class BikeNotSentException extends RuntimeException {

    public BikeNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

}
