package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.service.BikeService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class BikeGatewayImpl implements BikeGateway {

    @Inject
    BikeDAO bikeDAO;

    @Override
    public Bike nextBikeAvailableByPosition(BikeRequest bikeRequest) {
        List<Bike> availableBikes = bikeDAO.findAllAvailable();
        Bike closestBike = null;
        double closestDistance = Double.MAX_VALUE;

        for (Bike bike : availableBikes) {
            if (!bike.isBooked()) {
                double distance = calculateDistance(bikeRequest.positionX(), bikeRequest.positionY(),
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

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
