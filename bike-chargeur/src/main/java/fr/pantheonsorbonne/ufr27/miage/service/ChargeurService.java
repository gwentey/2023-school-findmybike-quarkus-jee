package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;

public interface ChargeurService {
	void chargerLeBike(Bike bike);
	Zone findNearestZone(Bike bike);
	void priseEnChargeBike(Bike bike);
	void chargerBike(Bike bike);


	}
