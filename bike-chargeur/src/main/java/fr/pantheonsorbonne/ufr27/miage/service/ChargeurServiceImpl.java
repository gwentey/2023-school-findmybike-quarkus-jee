package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.ZoneDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ChargeurServiceImpl implements ChargeurService {
//test
	private double chargeurPosX = 2.3522;
	private double chargeurPosY = 48.8566;
	@Inject
	BikeGateway bikeGateway;
	@Inject
	ZoneDAO zoneDAO;


	@Override
	public void simulerBikeCharging(Bike bike) {

		double distance = calculateReelDistance(chargeurPosY, chargeurPosX,
				bike.getPositionY(), bike.getPositionX());
		System.out.println("Distance du vélo: " + distance + " kilomètres");

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

	private static final int RAYON_TERRE = 6371; // Rayon de la Terre en kilomètres

	private double calculateReelDistance(double lat1, double lon1, double lat2, double lon2) {
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return RAYON_TERRE * c; // Retourne la distance en kilomètres
	}


	@Override
	public Zone findNearestZone(Bike bike) {
		List<Zone> allZones = zoneDAO.findAllZones();
		Zone nearestZone = null;
		double nearestDistance = Double.MAX_VALUE;

		for (Zone zone : allZones) {
			double centerX = (zone.getLongitudePoint1() + zone.getLongitudePoint2() +
					zone.getLongitudePoint3() + zone.getLongitudePoint4()) / 4;
			double centerY = (zone.getLatitudePoint1() + zone.getLatitudePoint2() +
					zone.getLatitudePoint3() + zone.getLatitudePoint4()) / 4;

			double distance = calculateDistance(bike.getPositionX(), bike.getPositionY(), centerX, centerY);
			if (distance < nearestDistance) {
				nearestZone = zone;
				nearestDistance = distance;
			}
		}

		if (nearestZone != null) {
			System.out.println("La zone la plus proche: " + nearestZone.getId() +
					" | Position du vélo: " + bike.getPositionX() + " " + bike.getPositionY());
		} else {
			System.out.println("Aucune zone trouvée proche du vélo.");
		}
		return nearestZone;
	}

	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

}
