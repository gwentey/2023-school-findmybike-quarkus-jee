package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;

public interface BikeGateway {

	void simulerBikeCharging(Bike bike);
	void sendAChargeConfirmation(Bike bike);
	void sendAChargeEnd(Bike bike);

	}

