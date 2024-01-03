package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@ApplicationScoped
public class BikeGatewayImpl implements BikeGateway {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    ObjectMapper objectMapper;

    /**
     * Recherche et renvoie le vélo disponible le plus proche de la position spécifiée.
     * Cette méthode envoie une demande à la queue 'M1.bike-localisation' avec les coordonnées
     * spécifiées et attend une réponse avec les détails du vélo disponible.
     *
     * @param positionX La position X (latitude) pour la localisation du vélo.
     * @param positionY La position Y (longitude) pour la localisation du vélo.
     * @return Un objet Bike correspondant au vélo disponible le plus proche, ou null si aucun vélo n'est trouvé
     *         ou en cas d'erreur ou de timeout.
     */
    public Bike nextBikeAvailableByPosition(Double positionX, Double positionY) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            BikeRequest bikeRequest = new BikeRequest(positionX, positionY, 1);
            String jsonString = objectMapper.writeValueAsString(bikeRequest);

            String correlationId = UUID.randomUUID().toString();
            String responseQueueName = "M1.bike-response";

            TextMessage message = context.createTextMessage(jsonString);
            message.setJMSCorrelationID(correlationId);

            Destination responseQueue = context.createQueue(responseQueueName);
            message.setJMSReplyTo(responseQueue);

            context.createProducer().send(context.createQueue("M1.bike-localisation?exchangePattern=InOut"), message);

            System.out.println("1 - Sent request");
            System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
            System.out.println("\tMessage ID: " + message.getJMSMessageID());
            System.out.println("\tCorrel. ID: " + message.getJMSCorrelationID());
            System.out.println("\tReply to:   " + message.getJMSReplyTo());
            System.out.println("\tContents:   " + message.getText());

            // Attendre la réponse
            JMSConsumer consumer = context.createConsumer(responseQueue);
            Message responseMessage = consumer.receive(5000);

            if (responseMessage != null) {
                System.out.println("1 - Response receive request");
                System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
                System.out.println("\tMessage ID: " + responseMessage.getJMSMessageID());
                System.out.println("\tCorrel. ID: " + responseMessage.getJMSCorrelationID());
                System.out.println("\tReply to:   " + responseMessage.getJMSReplyTo());
                System.out.println("\tContents:   " + ((TextMessage) responseMessage).getText());

                if (responseMessage instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) responseMessage;
                    String responseText = textMessage.getText();
                    System.out.println("Contenu du message reçu : " + responseText);
                    try {
                        Bike bike = objectMapper.readValue(responseText, Bike.class);
                        return bike;
                    } catch (IOException e) {
                        System.err.println("Erreur lors de la désérialisation du message : " + e.getMessage());
                    }
                } else {
                    System.err.println("Le message reçu n'est pas de type TextMessage");
                }
            } else {
                System.err.println("Aucun message reçu après l'attente (timeout)");
            }

        } catch (JsonProcessingException | JMSException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
