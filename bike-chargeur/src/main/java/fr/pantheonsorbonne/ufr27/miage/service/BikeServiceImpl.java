package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class BikeServiceImpl implements BikeService {

	@Inject
	BikeGateway bikeGateway;

	@Override
	public void simulerBikeCharging(Bike bike) {
		bikeGateway.sendAChargeConfirmation(bike);
		new Thread(() -> {
			try {
				int initialBatteryLevel = bike.getBatterie();
				int remainingCharge = 100 - initialBatteryLevel;
				int secondsToFullCharge = remainingCharge / 2;

				for (int i = 0; i < secondsToFullCharge; i++) {
					Thread.sleep(1000);
					int currentCharge = initialBatteryLevel + (i + 1) * 2;
					System.out.println("Vélo ID: " + bike.getIdBike() + " - " + currentCharge + "% chargé");
				}
				bike.setBatterie(100);
				System.out.println("Chargement terminé pour le vélo ID: " + bike.getIdBike());
				bikeGateway.sendAChargeEnd(bike);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}).start();
	}

}
