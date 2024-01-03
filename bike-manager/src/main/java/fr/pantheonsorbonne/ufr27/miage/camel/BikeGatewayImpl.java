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

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class BikeGatewayImpl implements BikeGateway {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    BikeDAO bikeDAO;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Bike nextBikeAvailableByPosition(BikeRequest bikeRequest) {
        List<Bike> allBikes = bikeDAO.findAll();
        if (!allBikes.isEmpty()) {
            Random random = new Random();
            int randomIndex = -1;
            while (randomIndex < 0 || randomIndex >= allBikes.size()) {
                randomIndex = random.nextInt(allBikes.size());
            }
            Bike bike = allBikes.get(randomIndex);
            return bike;
        }
        return null;
    }
}
