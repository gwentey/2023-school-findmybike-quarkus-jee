package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    BikeGatewayImpl bikeGateway;
    @Mock
    UserDAO userDAO;
    @Mock
    BikeDAOImpl bikeDAO;
    @Mock
    BookingDAO bookingDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(bikeGateway, userDAO, bikeDAO, bookingDAO);
    }

    @Test
    public void testBookABikeSuccess() {
        long userId = 1;
        int bikeId = 8;
        User user = new User();
        user.setId(userId);
        Bike bike = new Bike();
        bike.setIdBike(bikeId);

        Bike expectedBike = new Bike();
        expectedBike.setIdBike(8);
        expectedBike.setPositionX(2.29435);
        expectedBike.setPositionY(48.858844);
        expectedBike.setBatterie(100);
        expectedBike.setManagerId(1);

        when(userDAO.findById(userId)).thenReturn(user);
        when(bikeDAO.findById(bikeId)).thenReturn(bike);
        when(bookingDAO.isBikeBooked(bikeId)).thenReturn(false);
        when(bikeGateway.getABikeById(8)).thenReturn(expectedBike);
        when(bikeGateway.bookBikeById(8)).thenReturn(true);

        Booking result = userService.bookABike(userId, bikeId);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
        assertEquals(bikeId, result.getBike().getIdBike());
        verify(bookingDAO).save(any(Booking.class));
    }

    @Test
    public void testBookABikeBikeNotFound() {
        long userId = 1;
        int bikeId = 99;
        User user = new User();
        user.setId(userId);

        when(userDAO.findById(userId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.bookABike(userId, bikeId);
        });

        assertEquals("Utilisateur ou vélo introuvable", exception.getMessage());
    }

    @Test
    void testBookABikeBookingException() {
        long userId = 1;
        int bikeId = 8;
        User user = new User();
        user.setId(userId);
        Bike bike = new Bike();
        bike.setIdBike(bikeId);

        when(userDAO.findById(userId)).thenReturn(user);
        when(bikeDAO.findById(bikeId)).thenReturn(bike);
        when(bookingDAO.isBikeBooked(bikeId)).thenReturn(false);
        when(bikeGateway.getABikeById(bikeId)).thenReturn(bike);
        when(bikeGateway.bookBikeById(bikeId)).thenThrow(new RuntimeException("Erreur côté Manager"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.bookABike(userId, bikeId);
        });

        assertEquals("Erreur côté Manager", exception.getMessage());
        verify(bookingDAO, never()).save(any(Booking.class));
    }

    @Test
    public void testReturnBikeSuccess() {
        long userId = 1;
        int bikeId = 100;
        User user = new User();
        user.setId(userId);
        Bike bike = new Bike();
        bike.setIdBike(bikeId);
        Booking booking = new Booking();

        when(userDAO.findById(userId)).thenReturn(user);
        when(bikeDAO.findById(bikeId)).thenReturn(bike);
        when(bookingDAO.findBookingByUserIdAndBikeId(userId, bikeId)).thenReturn(booking);

        userService.returnBike(userId, bike);

        verify(bikeDAO).merge(bike);
        verify(bikeGateway).returnABike(bike);
        verify(bookingDAO).deleteBookingByUserIdAndBikeId(userId, bikeId);
    }

    @Test
    public void testReturnBikeUserNotFound() {
        long userId = 1;
        Bike bike = new Bike();
        bike.setIdBike(100);

        when(userDAO.findById(userId)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.returnBike(userId, bike);
        });

        assertEquals("Utilisateur introuvable", exception.getMessage());
    }

    @Test
    void testReturnBikeNoReservationFound() {
        long userId = 1;
        int bikeId = 100;
        User user = new User();
        user.setId(userId);
        Bike bike = new Bike();
        bike.setIdBike(bikeId);

        when(userDAO.findById(userId)).thenReturn(user);
        when(bikeDAO.findById(bikeId)).thenReturn(bike);
        when(bookingDAO.findBookingByUserIdAndBikeId(userId, bikeId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.returnBike(userId, bike);
        });

        assertEquals("Aucune réservation trouvée pour cet utilisateur et ce vélo", exception.getMessage());
        verify(bikeDAO, never()).merge(any(Bike.class));
        verify(bikeGateway, never()).returnABike(any(Bike.class));
        verify(bookingDAO, never()).deleteBookingByUserIdAndBikeId(anyLong(), anyInt());
    }

    @Test
    void testGenererItineraireUrl() {
        double userPosX = 1.0;
        double userPosY = 2.0;
        Bike bike = new Bike();
        bike.setPositionX(3.0);
        bike.setPositionY(4.0);

        String expectedUrl = "https://www.google.fr/maps/dir/2.0,1.0/4.0,3.0/data=!3m1!4b1!4m2!4m1!3e2?entry=ttu";

        String actualUrl = userService.genererItineraireUrl(userPosX, userPosY, bike);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void testGenererUneUrlMaps() {
        Bike bike = new Bike();
        bike.setPositionX(3.0);
        bike.setPositionY(4.0);

        String expectedUrl = "https://www.google.fr/maps/dir/4.0,3.0";

        String actualUrl = userService.genererUneUrlMaps(bike);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void testGetABikeById() {
        int idBike = 1;
        Bike expectedBike = new Bike();
        expectedBike.setIdBike(idBike);

        when(bikeGateway.getABikeById(idBike)).thenReturn(expectedBike);

        Bike actualBike = userService.getABikeById(idBike);

        assertEquals(expectedBike, actualBike);
    }

    @Test
    void testNextBikeAvailableByPosition() {
        Double positionX = 1.0;
        Double positionY = 2.0;
        Bike expectedBike = new Bike();

        when(bikeGateway.nextBikeAvailableByPosition(positionX, positionY)).thenReturn(expectedBike);

        Bike actualBike = userService.nextBikeAvailableByPosition(positionX, positionY);

        assertEquals(expectedBike, actualBike);
    }
}