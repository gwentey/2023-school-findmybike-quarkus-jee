package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserDAO userDAO;

    @Mock
    BikeGatewayImpl bikeGateway;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testBikeAvailableEndpoint() {
        double positionX = 2.2932196427317164;
        double positionY = 48.85844443869412;

        given()
                .pathParam("positionX", positionX)
                .pathParam("positionY", positionY)
                .when()
                .get("http://localhost:8082/user/bike/available/{positionX}/{positionY}")
                .then()
                .statusCode(200)
                .body("idBike", equalTo(1))
                .body("positionX", equalTo(2.29435f))
                .body("positionY", equalTo(48.858844f))
                .body("batterie", equalTo(100))
                .body("managerId", equalTo(1));
    }




    /*
    @Test
    void bookVoid() throws UnsuficientQuotaForVenueException, NoSuchTicketException {


        bookingService.book(new Booking(1, 1, 0, 0, standingTransitionalTicker, seatingTransitionalTicker));
        verify(ticketDAO, never()).save(any(), any(), any());


    }

    @Test
    void bookOneStanding() throws UnsuficientQuotaForVenueException, NoSuchTicketException {


        bookingService.book(new Booking(1, 1, 1, 0, standingTransitionalTicker, seatingTransitionalTicker));
        verify(ticketDAO, times(1)).save(any(), any(), any());
        assertEquals(1, standingTransitionalTicker.size());
        assertEquals(0, seatingTransitionalTicker.size());


    }

    @Test
    void bookOneSeating() throws UnsuficientQuotaForVenueException, NoSuchTicketException {


        bookingService.book(new Booking(1, 1, 0, 1, standingTransitionalTicker, seatingTransitionalTicker));
        verify(ticketDAO, times(1)).save(any(), any(), any());
        assertEquals(0, standingTransitionalTicker.size());
        assertEquals(1, seatingTransitionalTicker.size());

    }

    @Test
    void bookNotEnough() throws UnsuficientQuotaForVenueException {

        assertThrows(UnsuficientQuotaForVenueException.class, () -> bookingService.book(new Booking(1, 1, 99, 99, standingTransitionalTicker, seatingTransitionalTicker)));
    }
    */

}