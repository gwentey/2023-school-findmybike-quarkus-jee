package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @Inject
    CamelContext camelContext;
    @Inject
    BikeGateway bikeHandler;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);

        /**
         * Cette route consomme des messages de la queue 'M1.bike-recharge', qui contiennent des informations
         * sur le/les vélos à recharger
         */
        from("sjms2:M1.bike-recharge")
                .autoStartup(isRouteEnabled)
                .unmarshal().json(Bike.class)
                .process(exchange -> {
                    Bike bike = exchange.getIn().getBody(Bike.class);

                    bikeHandler.simulerBikeCharging(bike);
                });

    }

}

