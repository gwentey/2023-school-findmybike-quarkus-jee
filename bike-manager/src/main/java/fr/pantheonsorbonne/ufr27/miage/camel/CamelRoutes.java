package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;

import fr.pantheonsorbonne.ufr27.miage.service.BikeService;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @Inject
    CamelContext camelContext;
    @Inject
    BikeService bikeService;
    @Inject
    ObjectMapper objectMapper;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);

        /**
         * Cette route consomme des messages de la queue 'M1.bike-localisation', qui contiennent des demandes
         * pour localiser les vélos disponibles à proximité d'une position spécifique
         */
        from("sjms2:M1.bike-localisation")
                .autoStartup(isRouteEnabled)
                .unmarshal().json(BikeRequest.class)
                .bean(bikeService, "nextBikeAvailableByPosition")
                .marshal().json()
                .process(exchange -> {
                    String responseJson = exchange.getIn().getBody(String.class);
                    exchange.getIn().setBody(responseJson);
                })
                .to("sjms2:M1.bike-response")
                .log("Message envoyé à la queue M1.bike-response avec Correlation ID: ${header.JMSCorrelationID}");

        /**
         * Cette route consomme des messages de la queue 'M1.bike-id', qui contiennent des demandes
         * pour connaitre un vélo à partir de son id
         */
        from("sjms2:M1.bike-id")
                .autoStartup(isRouteEnabled)
                .process(exchange -> {
                    int idBike = exchange.getIn().getHeader("idBike", Integer.class);
                    Bike bike = bikeService.getABikeById(idBike);
                    if (bike != null) {
                        String responseJson = objectMapper.writeValueAsString(bike);
                        exchange.getIn().setBody(responseJson);
                    } else {
                        exchange.getIn().setBody("Aucun vélo trouvé pour l'ID: " + idBike);
                    }
                })
                .to("sjms2:M1.bike-response-id")
                .log("Réponse envoyée pour la requête de vélo par ID à la queue M1.bike-response-id");

        /**
         * Cette route consomme des messages de la queue 'M1.bike-book', qui contiennent des demandes
         * pour réserver un vélo spécifique par son ID. La route traite la demande et renvoie une réponse
         * indiquant si la réservation a été réussie.
         */
        from("sjms2:M1.bike-book")
                .autoStartup(isRouteEnabled)
                .process(exchange -> {
                    int idBike = exchange.getIn().getHeader("bikeId", Integer.class);
                    // Logique de traitement pour réserver le vélo
                    boolean bookingResult = bikeService.bookBikeById(idBike);

                    // Préparer et envoyer la réponse
                    String responseText = Boolean.toString(bookingResult);
                    exchange.getIn().setBody(responseText);
                })
                .to("sjms2:M1.bike-book-response")
                .log("Réponse envoyée pour la réservation du vélo ID: ${header.bikeId} à la queue M1.bike-book-response");


        /**
         * Cette route consomme des messages de la queue 'M1.bike-actions', qui contiennent des informations
         * sur les vélos et une action a effectuer.
         */
        from("sjms2:M1.bike-actions")
                .autoStartup(isRouteEnabled)
                .unmarshal().json(Bike.class)
                .process(new BikeActionProcessor(bikeService))
                .log("Action traitée pour le vélo ID: ${body.idBike}");


        /**
         * Cette route consomme des messages de la queue 'M1.bike-return', qui contiennent des informations
         * sur les vélos retournés.
         */
        from("sjms2:M1.bike-return")
                .autoStartup(isRouteEnabled)
                .unmarshal().json(Bike.class)
                .process(exchange -> {
                    Bike bike = exchange.getIn().getBody(Bike.class);
                    bikeService.dropABike(bike);
                    System.out.println("Vélo retourné traité pour le vélo ID: " + bike.getIdBike());
                })
                .log("Traitement du vélo retourné pour le vélo ID: ${body.idBike}");

    }

        private static class BikeActionProcessor implements Processor {
            private BikeService bikeService;

            public BikeActionProcessor(BikeService bikeService) {
                this.bikeService = bikeService;
            }

            @Override
            public void process(Exchange exchange) throws Exception {
                Bike bike = exchange.getIn().getBody(Bike.class);
                String action = exchange.getIn().getHeader("BikeAction", String.class);

                switch (action) {
                    case "return":
                        bikeService.dropABike(bike);
                        break;
                    case "recharge-valid":
                        bikeService.setInCharge(bike);
                        break;
                    case "recharge-end":
                        bikeService.isCharged(bike);
                        break;
                    default:
                        throw new IllegalArgumentException("Action non reconnue : " + action);
                }
            }
        }

}

