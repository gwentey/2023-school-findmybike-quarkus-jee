package fr.pantheonsorbonne.ufr27.miage.exception;

public class BikeAlreadyBookedException extends Throwable {
    public BikeAlreadyBookedException(int bikeId) {
        super("Le vélo n°" + bikeId + " est déjà reservé!");
    }

}
