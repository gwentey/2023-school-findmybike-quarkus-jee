package fr.pantheonsorbonne.ufr27.miage.service;
import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;

import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


    @Test
    public void bookABike_Success() {
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
    public void bookABike_VeloExistePas() {
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





}