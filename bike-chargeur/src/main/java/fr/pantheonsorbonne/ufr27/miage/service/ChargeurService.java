package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;

public interface ChargeurService {
	void simulerBikeCharging(Bike bike);
	Zone findNearestZone(Bike bike);

}
