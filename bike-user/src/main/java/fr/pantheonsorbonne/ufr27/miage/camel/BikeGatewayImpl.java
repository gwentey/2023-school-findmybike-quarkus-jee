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

            context.createProducer().send(context.createQueue("M1.bike-localisation"), message);

            System.out.println("1 - Sent request");
            System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
            System.out.println("\tMessage ID: " + message.getJMSMessageID());
            System.out.println("\tCorrel. ID: " + message.getJMSCorrelationID());
            System.out.println("\tReply to:   " + message.getJMSReplyTo());
            System.out.println("\tContents:   " + message.getText());

            // Attendre la réponse
            JMSConsumer consumer = context.createConsumer(responseQueue);
            Message responseMessage = consumer.receive();

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

    /**
     * Recherche et renvoie un vélo spécifique par son identifiant.
     * Cette méthode envoie une demande à une queue (par exemple, 'M1.bike-id') avec l'identifiant du vélo
     * et attend une réponse avec les détails du vélo demandé.
     *
     * @param idBike L'identifiant du vélo à rechercher.
     * @return Un objet Bike correspondant au vélo demandé, ou null si aucun vélo n'est trouvé
     * ou en cas d'erreur ou de timeout.
     */
    @Override
    public Bike getABikeById(int idBike) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            // Préparation et envoi de la demande
            Message message = context.createMessage();
            message.setIntProperty("idBike", idBike);

            String correlationId = UUID.randomUUID().toString();
            message.setJMSCorrelationID(correlationId);

            Destination responseQueue = context.createQueue("M1.bike-response-id");

            context.createProducer().send(context.createQueue("M1.bike-id"), message);

            // Log de la demande
            System.out.println("Demande de vélo par ID envoyée. ID: " + idBike);

            Message responseMessage = context.createConsumer(responseQueue).receive();
            if (responseMessage instanceof TextMessage) {
                String responseText = ((TextMessage) responseMessage).getText();
                if (responseText.startsWith("Aucun vélo trouvé")) {
                    System.err.println(responseText);
                    return null;
                } else {
                    return objectMapper.readValue(responseText, Bike.class);
                }
            } else {
                System.err.println("Réponse non attendue ou timeout :" + System.currentTimeMillis());
            }
        } catch (JMSException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Réserve un vélo par son identifiant et attend la confirmation
     *
     * @param bikeId L'identifiant du vélo à réserver.
     * @return true si la réservation est réussie, false sinon.
     */
    @Override
    public boolean bookBikeById(int bikeId) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            Message message = context.createMessage();
            message.setIntProperty("bikeId", bikeId);

            String correlationId = UUID.randomUUID().toString();
            message.setJMSCorrelationID(correlationId);

            Destination responseQueue = context.createQueue("M1.bike-book-response");

            context.createProducer().send(context.createQueue("M1.bike-book"), message);

            System.out.println("Demande de vélo par ID envoyée. ID: " + bikeId);

            Message responseMessage = context.createConsumer(responseQueue).receive();
            if (responseMessage instanceof TextMessage) {
                String responseText = ((TextMessage) responseMessage).getText();
                return Boolean.parseBoolean(responseText);
            } else {
                System.err.println("Réponse non attendue ou timeout: " + System.currentTimeMillis());
                return false;
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Rend un vélo spécifique
     *
     * @param bike L'objet Bike représentant le vélo à rendre.
     */
    @Override
    public void returnABike(Bike bike) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            String jsonString = objectMapper.writeValueAsString(bike);
            TextMessage message = context.createTextMessage(jsonString);


            context.createProducer().send(context.createQueue("M1.bike-return"), message);

            System.out.println("Retour de vélo envoyée pour le vélo ID: " + bike.getIdBike());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
