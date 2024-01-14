package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.ChargeurGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.ZoneDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.ChargeInterruptedException;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class ChargeurServiceImpl implements ChargeurService {

	@Inject
	ChargeurGateway chargeurGateway;
	@Inject
	ZoneDAO zoneDAO;
	private double chargeurPosX = 2.3522;
	private double chargeurPosY = 48.8566;
	private static final int RAYON_TERRE = 6371;
	private static final int MAX_BIKES_EN_CHARGE = 3;

	private final Queue<Bike> queueChargement = new PriorityQueue<>(
			Comparator.comparingDouble(b -> calculateReelDistance(chargeurPosY, chargeurPosX, b.getPositionY(), b.getPositionX()))
	);
	private final List<Bike> bikesEnCharge = new ArrayList<>();

	@Override
	public synchronized void chargerBike(Bike bike) {
		queueChargement.add(bike);
		if (bikesEnCharge.size() < MAX_BIKES_EN_CHARGE) {
			Bike bikeACharger = queueChargement.poll();
			if (bikeACharger != null) {
				Thread chargeurThread = new Thread(() -> gererChargeur(bikeACharger));
				chargeurThread.start();
			}
		}
	}

	private void gererChargeur(Bike bike) {
		seDeplacerVersBike(bike);
		chargerLeBike(bike);
		Zone zoneLaPlusProche = findNearestZone(bike);
		seDeplacerVersZoneAvecBike(zoneLaPlusProche, bike);
		bikesEnCharge.remove(bike);
		chargeurGateway.sendAChargeEnd(bike);
	}

	@Override
	public void priseEnChargeBike(Bike bike) {
		chargeurGateway.sendAChargeConfirmation(bike);
		seDeplacerVersBike(bike);
		chargerLeBike(bike);
		Zone zoneLaPlusProche = findNearestZone(bike);
		seDeplacerVersZoneAvecBike(zoneLaPlusProche, bike);
		chargeurGateway.sendAChargeEnd(bike);
	}

	private void seDeplacerVersBike(Bike bike) {
		double distance = calculateReelDistance(chargeurPosY, chargeurPosX, bike.getPositionY(), bike.getPositionX());
		System.out.println("Distance du vélo: " + distance + " kilomètres");

		final double vitesseChargeur = 0.4; // 0.4 km/s

		double distanceRestante = distance;
		while (distanceRestante > 0) {
			try {
				Thread.sleep(1000); // Pause de 1 seconde
				distanceRestante -= vitesseChargeur;
				System.out.println("Déplacement du chargeur vers le vélo " + bike.getIdBike() + "... Distance restante: " + Math.max(distanceRestante, 0) + " km");
			} catch (InterruptedException e) {
                try {
                    throw new ChargeInterruptedException("La charge a été interrompue");
                } catch (ChargeInterruptedException ex) {
					ex.printMessage();
					Thread.currentThread().interrupt();
					return;                }
            }
		}

		System.out.println("Le chargeur est arrivé à la position du vélo.");
	}

	public void chargerLeBike(Bike bike) {
		int initialBatteryLevel = bike.getBatterie();
		int remainingCharge = 100 - initialBatteryLevel;
		int secondsToFullCharge = remainingCharge / 2;

		for (int i = 0; i < secondsToFullCharge; i++) {
			try {
				Thread.sleep(1000);
				int currentCharge = initialBatteryLevel + (i + 1) * 2;
				System.out.println("Vélo ID: " + bike.getIdBike() + " - " + currentCharge + "% chargé");
			} catch (InterruptedException e) {
				try {
					throw new ChargeInterruptedException("La charge a été interrompue");
				} catch (ChargeInterruptedException ex) {
					ex.printMessage();
					Thread.currentThread().interrupt();
					return;                }
			}
		}

		bike.setBatterie(100);
		System.out.println("Chargement terminé pour le vélo ID: " + bike.getIdBike());
	}

	private void seDeplacerVersZoneAvecBike(Zone zone, Bike bike) {
		if (zone != null) {
			// Calcul du centre de la zone
			double centerX = (zone.getLongitudePoint1() + zone.getLongitudePoint2() +
					zone.getLongitudePoint3() + zone.getLongitudePoint4()) / 4;
			double centerY = (zone.getLatitudePoint1() + zone.getLatitudePoint2() +
					zone.getLatitudePoint3() + zone.getLatitudePoint4()) / 4;

			// Calculez la distance entre la position du vélo et le centre de la zone
			double distance = calculateReelDistance(bike.getPositionY(), bike.getPositionX(), centerY, centerX);
			System.out.println("Distance de la zone: " + distance + " kilomètres avec le vélo");

			final double vitesseChargeur = 0.4; // Vitesse du chargeur en km/s

			double distanceRestante = distance;
			while (distanceRestante > 0) {
				try {
					Thread.sleep(1000); // Pause de 1 seconde
					distanceRestante -= vitesseChargeur;
					System.out.println("Déplacement du chargeur vers la zone " + zone.getId() + " ... Distance restante: " + Math.max(distanceRestante, 0) + " km");
				} catch (InterruptedException e) {
					try {
						throw new ChargeInterruptedException("La charge a été interrompue");
					} catch (ChargeInterruptedException ex) {
						ex.printMessage();
						Thread.currentThread().interrupt();
						return;
					}
				}
			}

			System.out.println("Le chargeur est arrivé à la zone avec le vélo.");
		}
	}


	private double calculateReelDistance(double lat1, double lon1, double lat2, double lon2) {
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return RAYON_TERRE * c;
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

			double distance = calculateReelDistance(centerY, centerX, bike.getPositionY(), bike.getPositionX());

			System.out.println("Bike numero " + bike.getIdBike() + " distance entre zone numero " + zone.getId() + " : " + distance + " km");

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

}
