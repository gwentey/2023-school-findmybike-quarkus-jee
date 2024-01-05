package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class BikeServiceImpl implements BikeService {

	@Inject
	BikeDAO bikeDAO;
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

	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
