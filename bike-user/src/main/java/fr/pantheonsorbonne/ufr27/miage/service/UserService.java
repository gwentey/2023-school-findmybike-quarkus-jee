package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;

import java.math.BigDecimal;

public interface UserService {

	Booking book(int userId, int idBike);

	public Bike getABikeById(int idBike);

	public Bike nextBikeAvailableByPosition(Double positionX, Double positionY);

	public User validateUser(String pseudo, String password);

	}
