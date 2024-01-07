package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BikeServiceImplTest {
    @Mock
    BikeGatewayImpl bikeGateway;
    @InjectMocks
    BikeServiceImpl bikeService;

    @Mock
    BikeDAOImpl bikeDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testGetABikeById() {
        Bike bike = new Bike();
        bike.setIdBike(1);
        when(bikeDAO.findById(1)).thenReturn(bike);

        Bike result = bikeService.getABikeById(1);
        assertNotNull(result);
        assertEquals(1, result.getIdBike());
    }


    @Test
    void testBookBikeById() {
        Bike bike = new Bike();
        bike.setIdBike(1);
        when(bikeDAO.findById(1)).thenReturn(bike);

        assertTrue(bikeService.bookBikeById(1));
        verify(bikeDAO).merge(bike);
        assertTrue(bike.isBooked());
    }


    @Test
    void testSetInCharge() {
        Bike bike = new Bike();
        bike.setIdBike(1);
        when(bikeDAO.findById(1)).thenReturn(bike);

        assertTrue(bikeService.setInCharge(bike));
        assertTrue(bike.getInCharge());
        verify(bikeDAO).merge(bike);
    }





}