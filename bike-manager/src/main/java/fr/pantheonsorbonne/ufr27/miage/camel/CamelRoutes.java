package fr.pantheonsorbonne.ufr27.miage.camel;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.service.BikeService;
import io.quarkus.logging.Log;
import jakarta.jms.JMSException;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;

import static net.bytebuddy.implementation.MethodDelegation.to;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @Inject
    CamelContext camelContext;
    @Inject
    BikeGatewayImpl bikeHandler;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);

        from("sjms2:M1.bike-test?exchangePattern=InOut")
                .autoStartup(isRouteEnabled)
                .unmarshal().json(BikeRequest.class)
                .bean(bikeHandler, "nextBikeAvailableByPosition")
                .marshal().json()
                .setBody(simple("${body}"))
                .log("message recu avec succes")
                .to("sjms2:M1.bike-response");

    }

}

