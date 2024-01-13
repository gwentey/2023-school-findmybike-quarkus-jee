package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.exception.BikeNotSentException;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.service.BikeService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class BikeGatewayImpl implements BikeGateway {

    @Inject
    BikeService bikeService;
    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    ObjectMapper objectMapper;

    /**
     * Envoie une demande de recharge pour un vélo
     *
     * @param bike L'objet Bike représentant le vélo à recharger.
     */
    @Override
    public void sendABikeInCharge(Bike bike) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            String jsonString = objectMapper.writeValueAsString(bike);
            TextMessage message = context.createTextMessage(jsonString);

            context.createProducer().send(context.createQueue("M1.bike-recharge"), message);

            System.out.println("Vélo envoyée pour la recharge ID: " + bike.getIdBike());

        } catch (IOException e) {
            throw new BikeNotSentException("Le vélo ID: " + bike.getIdBike() + " n'a pas été envoyé pour recharge", e);
        }
    }

    @Override
    public Bike nextBikeAvailableByPosition(BikeRequest bikeRequest) {
        return bikeService.nextBikeAvailableByPosition(bikeRequest);
    }

    @Override
    public Bike getABikeById(int idBike) {
       return bikeService.getABikeById(idBike);
    }

    @Override
    public boolean bookBikeById(int bikeId) {
        return bikeService.bookBikeById(bikeId);
    }

    @Override
    public boolean dropABike(Bike bike) {
        return bikeService.dropABike(bike);
    }

    @Override
    public boolean setInCharge(Bike bike) {
        return bikeService.setInCharge(bike);
    }

    @Override
    public boolean isCharged(Bike bike) {
        return bikeService.isCharged(bike);
    }
}
