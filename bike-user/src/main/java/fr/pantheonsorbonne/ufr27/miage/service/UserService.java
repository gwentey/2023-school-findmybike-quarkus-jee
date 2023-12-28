package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;

public interface UserService {

	Booking book(int userId, int idBike);

	public Bike getABikeById(int idBike);

}
