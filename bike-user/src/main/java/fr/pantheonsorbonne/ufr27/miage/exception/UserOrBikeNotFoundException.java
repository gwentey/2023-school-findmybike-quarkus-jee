package fr.pantheonsorbonne.ufr27.miage.exception;

public class UserOrBikeNotFoundException extends RuntimeException {
    public UserOrBikeNotFoundException() {
        super("Utilisateur ou vélo introuvable");
    }
}
