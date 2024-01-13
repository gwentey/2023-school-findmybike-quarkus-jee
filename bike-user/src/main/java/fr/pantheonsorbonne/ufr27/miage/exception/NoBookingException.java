package fr.pantheonsorbonne.ufr27.miage.exception;

public class NoBookingException extends Exception {

    public static class NoBookingUserIDBikeID extends RuntimeException {

        private String message;

        public NoBookingUserIDBikeID(int userID, int bikeID) {
            super("No booking found relative to user n°" + userID + " and Bike n°" + bikeID);
            this.message = super.getMessage();
        }

        public void printMessage() {
            System.out.println(message);
        }
    }
}
