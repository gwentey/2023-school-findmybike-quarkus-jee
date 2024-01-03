package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;

public interface UserService {

	Booking bookABike(long userId, int idBike);

	public Bike getABikeById(int idBike);

	public Bike nextBikeAvailableByPosition(Double positionX, Double positionY);

}
