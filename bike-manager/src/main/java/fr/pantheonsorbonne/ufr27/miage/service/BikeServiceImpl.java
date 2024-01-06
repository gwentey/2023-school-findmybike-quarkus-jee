package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.ZoneDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class BikeServiceImpl implements BikeService {

	@Inject
	BikeDAO bikeDAO;
	@Inject
	ZoneDAO zoneDAO;
	@Inject
	BikeGateway bikeGateway;

	@Override
	public Bike getABikeById(int idBike) {
		Bike bike = bikeDAO.findById(idBike);
		if (bike != null) {
			return bike;
		} else {
			return null;
		}
	}

	@Override
	public boolean bookBikeById(int bikeId) {
		Bike bike = bikeDAO.findById(bikeId);
		bike.setBooked(true);
		bikeDAO.merge(bike);
		return true;
	}

	@Override
	public boolean setInCharge(Bike bike) {
		Bike b = bikeDAO.findById(bike.getIdBike());

		if (b != null) {
			b.setBatterie(bike.getBatterie());
			b.setPositionX(bike.getPositionX());
			b.setPositionY(bike.getPositionY());
			b.setInCharge(true);

			bikeDAO.merge(b);
			return true;
		} else {
			System.err.println("Vélo non trouvé avec l'ID: " + bike.getIdBike());
			return false;
		}
	}


	@Override
	public boolean isCharged(Bike bike) {
		Bike b = bikeDAO.findById(bike.getIdBike());

		if (b != null) {
			b.setBatterie(bike.getBatterie());
			b.setPositionX(bike.getPositionX());
			b.setPositionY(bike.getPositionY());
			b.setInCharge(false);

			bikeDAO.merge(b);
			return true;
		} else {
			System.err.println("Vélo non trouvé avec l'ID: " + bike.getIdBike());
			return false;
		}
	}


	@Override
	public boolean dropABike(Bike bike) {
		Bike b = bikeDAO.findById(bike.getIdBike());

		if (b != null) {
			this.findZoneForBike(bike);
			this.findNearestZone(bike);
			b.setBatterie(bike.getBatterie());
			b.setPositionX(bike.getPositionX());
			b.setPositionY(bike.getPositionY());
			b.setBooked(false);

			if(b.getBatterie() <= 85) {
				System.out.println("Vélo envoyé à la recharge");
				bikeGateway.sendABikeInCharge(b);
			}

			bikeDAO.merge(b);
			return true;
		} else {
			System.err.println("Vélo non trouvé avec l'ID: " + bike.getIdBike());
			return false;
		}
	}


	@Override
	public Bike nextBikeAvailableByPosition(BikeRequest bikeRequest) {
		List<Bike> availableBikes = bikeDAO.findAllAvailable();
		Bike closestBike = null;
		double closestDistance = Double.MAX_VALUE;

		for (Bike bike : availableBikes) {
			if (!bike.isBooked()) {
				double distance = this.calculateDistance(bikeRequest.positionX(), bikeRequest.positionY(),
						bike.getPositionX(), bike.getPositionY());

				if (distance < closestDistance) {
					closestBike = bike;
					closestDistance = distance;
				}
			}
		}
		return closestBike;
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





	@Override
	public Zone findZoneForBike(Bike b) {
		if (b != null) {
			List<Zone> allZones = zoneDAO.findAllZones();
			for (Zone zone : allZones) {
				if (isInBounds(b.getPositionX(), b.getPositionY(), zone)) {
					System.out.println("VELO DEPOSER DANS LA ZONE :" + zone.getId());
					return zone;
				}
			}
		}
		System.out.println("VELO DEPOSER DANS AUCUNE ZONE :");

		return null;
	}

	private boolean isInBounds(double posX, double posY, Zone zone) {
		double[] latitudes = {zone.getLatitudePoint1(), zone.getLatitudePoint2(), zone.getLatitudePoint3(), zone.getLatitudePoint4()};
		double[] longitudes = {zone.getLongitudePoint1(), zone.getLongitudePoint2(), zone.getLongitudePoint3(), zone.getLongitudePoint4()};

		int i, j;
		boolean result = false;
		for (i = 0, j = latitudes.length - 1; i < latitudes.length; j = i++) {
			if ((latitudes[i] > posY) != (latitudes[j] > posY) &&
					(posX < (longitudes[j] - longitudes[i]) * (posY - latitudes[i]) / (latitudes[j] - latitudes[i]) + longitudes[i])) {
				result = !result;
			}
		}
		System.out.println("Checking bike at (" + posX + ", " + posY + ") for zone " + zone.getId() + ": " + result);
		return result;
	}


	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
