package fr.pantheonsorbonne.ufr27.miage.exception;

import jakarta.persistence.NoResultException;

public class NoUserFound extends Exception {

    public static class NoUserFoundByUsername extends NoResultException {

        private final String message;

        public NoUserFoundByUsername(String username) {
            super("Aucun utilisateur trouvé pour le nom d'utilisateur suivant: " + username);
            this.message = super.getMessage();
        }

        public void printMessage(){
            System.out.println(message);
        }
    }

    public static class NoUserFoundByID extends RuntimeException {

        private final String message;

        public NoUserFoundByID(int userID) {
            super("Aucun utilisateur trouvé pour l'ID suivant : " + userID);
            this.message = super.getMessage();
        }
    }
}
