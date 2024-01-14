package fr.pantheonsorbonne.ufr27.miage.exception;

public class NoBookingException extends Exception {

    public static class NoBookingUserIDBikeID extends RuntimeException {

        private String message;

        public NoBookingUserIDBikeID() {
            super("Aucune réservation trouvée pour cet utilisateur et ce vélo");
            this.message = super.getMessage();
        }

        public void printMessage() {
            System.out.println(message);
        }
    }
}
