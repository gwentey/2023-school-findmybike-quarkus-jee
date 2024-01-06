package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;

public interface BikeService {
	Bike getABikeById(int idBike);
	boolean bookBikeById(int bikeId);
	boolean dropABike(Bike bike);
	Bike nextBikeAvailableByPosition(BikeRequest bikeRequest);
	boolean setInCharge(Bike b);
	boolean isCharged(Bike b);
	Zone findZoneForBike(Bike bike);
	Zone findNearestZone(Bike bike);



	}
