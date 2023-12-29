package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;

import java.math.BigDecimal;

@ApplicationScoped
public class BikeGatewayImpl implements BikeGateway {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public void nextBikeAvailableByPosition(Double positionX, Double positionY) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            // Créer la demande de vélo
            BikeRequest bikeRequest = new BikeRequest(positionX, positionY, 1);
            String jsonString = objectMapper.writeValueAsString(bikeRequest);

            // Créer le message JMS
            Message message = context.createTextMessage(jsonString);

            // Définir la file d'attente de réponse (JMSReplyTo)
            TemporaryQueue replyQueue = context.createTemporaryQueue();
            message.setJMSReplyTo(replyQueue);

            // Générer une correlationId unique
            String correlationId = "ID" + System.currentTimeMillis();
            message.setJMSCorrelationID(correlationId);

            // Envoyer la demande de vélo à la file d'attente principale
            context.createProducer().send(context.createQueue("M1.bike-test"), message);

            Log.info("Message nextBikeAvailableByPosition envoyé");
            System.out.println(message);
        } catch (JsonProcessingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
