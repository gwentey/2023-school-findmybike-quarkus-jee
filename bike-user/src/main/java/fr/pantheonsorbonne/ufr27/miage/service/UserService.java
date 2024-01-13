package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.exception.BikeAlreadyBookedException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoBookingException;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;

public interface UserService {

	Booking bookABike(long userId, int idBike) throws BikeAlreadyBookedException;

	Bike getABikeById(int idBike);

	Bike nextBikeAvailableByPosition(Double positionX, Double positionY);
	void returnBike(long userId, Bike bike) throws NoBookingException.NoBookingUserIDBikeID;
	String genererItineraireUrl(double userPosX, double userPosY, Bike bike);
	String genererUneUrlMaps(Bike bike);

}
