package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserDAO userDAO;

    @Mock
    BikeGateway bikeGateway;

    @BeforeEach
    public void setup() {

    }

    @Test
    void nextBikeAvailableByPosition() {

        userService.nextBikeAvailableByPosition(48.858844, 2.294350);

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