package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.exception.ChargeValidationNotSentException;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.service.ChargeurService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.TextMessage;

import java.io.IOException;

@ApplicationScoped
public class ChargeurGatewayImpl implements ChargeurGateway {
    @Inject
    ConnectionFactory connectionFactory;
    @Inject
    ObjectMapper objectMapper;
    @Inject
    ChargeurService chargeurService;

    @Override
    public void chargerBike(Bike bike) {
        chargeurService.priseEnChargeBike(bike);
    }

    /**
     * Envoie un message de prise en charge du vélo à charger
     *
     * @param bike L'objet Bike représentant le vélo qui va etre chargé
     */
    @Override
    public void sendAChargeConfirmation(Bike bike) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            String jsonString = objectMapper.writeValueAsString(bike);
            TextMessage message = context.createTextMessage(jsonString);

            context.createProducer().send(context.createQueue("M1.bike-recharge-valid"), message);

            System.out.println("La validation a été envoyé pour la recharge ID: " + bike.getIdBike());

        } catch (IOException e) {
            throw new ChargeValidationNotSentException("La validation n'a pas été envoyée pour la recharge ID: " + bike.getIdBike(),e);
        }
    }

    /**
     * Envoie un message indiquant la fin de la charge
     *
     * @param bike L'objet Bike représentant le vélo qui a été chargé
     */
    @Override
    public void sendAChargeEnd(Bike bike) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            String jsonString = objectMapper.writeValueAsString(bike);
            TextMessage message = context.createTextMessage(jsonString);

            context.createProducer().send(context.createQueue("M1.bike-recharge-end"), message);

            System.out.println("La validation a été envoyé pour la recharge ID: " + bike.getIdBike());

        } catch (IOException e) {
            throw new ChargeValidationNotSentException("La validation n'a pas été envoyée pour la recharge ID: " + bike.getIdBike(),e);
        }
    }

}
