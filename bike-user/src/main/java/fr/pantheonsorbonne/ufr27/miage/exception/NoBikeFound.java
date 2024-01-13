package fr.pantheonsorbonne.ufr27.miage.exception;

public class NoBikeFound extends Throwable {
    public static class NoBikeFoundByID extends RuntimeException {

        public NoBikeFoundByID(int bikeID){
            super("No bike found for this ID : " + bikeID);
            printMessage("No bike found for this ID : " + bikeID);
        }

        public void printMessage(String message) {
            System.out.println(message);
        }

    }
}
