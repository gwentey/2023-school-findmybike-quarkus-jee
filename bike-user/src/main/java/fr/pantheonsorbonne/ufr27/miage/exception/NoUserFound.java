package fr.pantheonsorbonne.ufr27.miage.exception;

import jakarta.persistence.NoResultException;

public class NoUserFound extends Exception {

    public static class NoUserFoundByUsername extends NoResultException {

        private final String message;

        public NoUserFoundByUsername(String username) {
            super("No user found for the following username : " + username);
            this.message = super.getMessage();
        }

        public void printMessage(){
            System.out.println(message);
        }
    }

    public static class NoUserFoundByID extends RuntimeException {

        private final String message;

        public NoUserFoundByID(int userID) {
            super("No user found for the following ID : " + userID);
            this.message = super.getMessage();
        }

        public void printMessage(){
            System.out.println(message);
        }
    }
}
