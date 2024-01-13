package fr.pantheonsorbonne.ufr27.miage.exception;

public class BikeAlreadyBookedException extends Throwable {
    public BikeAlreadyBookedException(int bikeId) {
        super("The bike n°" + bikeId + " is already booked !");
    }

}
