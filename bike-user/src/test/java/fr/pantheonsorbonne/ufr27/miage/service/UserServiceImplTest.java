package fr.pantheonsorbonne.ufr27.miage.service;
import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    BikeGatewayImpl bikeGateway;

    @BeforeEach
    public void setup() {

        // testNextBikeAvailableByPosition
        Bike expectedBike = new Bike();
        expectedBike.setIdBike(9);
        expectedBike.setPositionX(2.29435);
        expectedBike.setPositionY(48.858844);
        expectedBike.setBatterie(100);
        expectedBike.setManagerId(1);

        lenient().when(bikeGateway.nextBikeAvailableByPosition(2.2932196427317164, 48.85844443869412)).thenReturn(expectedBike);

        // testGetABikeById
        Bike expectedBikeT = new Bike();
        expectedBikeT.setIdBike(4);
        expectedBikeT.setPositionX(2.295);
        expectedBikeT.setPositionY(48.8738);
        expectedBikeT.setBatterie(100);
        expectedBikeT.setManagerId(2);

        lenient().when(bikeGateway.getABikeById(4)).thenReturn(expectedBikeT);

    }

    @Test
    public void testNextBikeAvailableByPosition() {
        Bike result = userService.nextBikeAvailableByPosition(2.2932196427317164, 48.85844443869412);
        assertEquals(9, result.getIdBike());
        assertEquals(2.29435, result.getPositionX());
        assertEquals(48.858844, result.getPositionY());
        assertEquals(100, result.getBatterie());
        assertEquals(1, result.getManagerId());
    }

    @Test
    public void testGetABikeById() {
        Bike result = userService.getABikeById(4);
        assertEquals(4, result.getIdBike());
        assertEquals(2.295, result.getPositionX());
        assertEquals(48.8738, result.getPositionY());
        assertEquals(100, result.getBatterie());
        assertEquals(2, result.getManagerId());
    }


}