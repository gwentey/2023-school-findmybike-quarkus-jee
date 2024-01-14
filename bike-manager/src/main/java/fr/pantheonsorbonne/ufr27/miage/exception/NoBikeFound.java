package fr.pantheonsorbonne.ufr27.miage.exception;

public class NoBikeFound extends Throwable {
    public static class NoBikeFoundByID extends RuntimeException {

        public NoBikeFoundByID(int bikeID){
            super("Aucun vélo trouvé pour cet identifiant :" + bikeID);
            printMessage("Aucun vélo trouvé pour cet identifiant : " + bikeID);
        }

        public void printMessage(String message) {
            System.out.println(message);
        }

    }
}
