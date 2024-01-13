package fr.pantheonsorbonne.ufr27.miage;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.ZoneDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;
import fr.pantheonsorbonne.ufr27.miage.service.BikeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BikeServiceImplTest {

    @InjectMocks
    BikeServiceImpl bikeService;

    @Mock
    BikeGatewayImpl bikeGateway;

    @Mock
    BikeDAOImpl bikeDAO;

    @Mock
    ZoneDAOImpl zoneDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetABikeById() {
        Bike bike = new Bike();
        bike.setIdBike(1);

        when(bikeDAO.findById(1)).thenReturn(bike);
        when(bikeDAO.findById(2)).thenReturn(null);

        Bike result = bikeService.getABikeById(1);
        assertNotNull(result);
        assertEquals(1, result.getIdBike());
        assertNull(bikeService.getABikeById(2));
    }

    @Test
    void testBookBikeById() {
        Bike bike = new Bike();
        bike.setIdBike(1);

        when(bikeDAO.findById(1)).thenReturn(bike);

        bikeService.bookBikeById(1);
        assertTrue(bike.isBooked());

        verify(bikeDAO).findById(1);
        verify(bikeDAO).merge(bike);
    }

    @Test
    void testSetInCharge() {
        Bike bike = new Bike();
        bike.setIdBike(1);
        bike.setBatterie(40);
        bike.setPositionX(1.0);
        bike.setPositionY(2.0);

        when(bikeDAO.findById(1)).thenReturn(bike);

        assertTrue(bikeService.setInCharge(bike));
        assertTrue(bike.getInCharge());
        assertFalse(bikeService.setInCharge(new Bike()));

        verify(bikeDAO).merge(bike);
    }

    @Test
    void testIsCharged() {
        Bike bike = new Bike();
        bike.setIdBike(1);
        bike.setBatterie(80);
        bike.setPositionX(1.0);
        bike.setPositionY(2.0);

        when(bikeDAO.findById(1)).thenReturn(bike);

        assertTrue(bikeService.isCharged(bike));
        assertFalse(bike.getInCharge());
        assertEquals(80, bike.getBatterie());
        assertFalse(bikeService.isCharged(new Bike()));

        verify(bikeDAO).merge(bike);
    }

    @Test
    void testDropABike() {
        Bike bike = new Bike();
        bike.setIdBike(1);
        bike.setBatterie(70);
        bike.setPositionX(3.0);
        bike.setPositionY(4.0);

        when(bikeDAO.findById(1)).thenReturn(bike);

        assertTrue(bikeService.dropABike(bike));
        assertFalse(bike.isBooked());

        verify(bikeDAO).merge(bike);
        verify(bikeGateway).sendABikeInCharge(bike);

        assertFalse(bikeService.dropABike(new Bike()));
    }

    @Test
    void testNextBikeAvailableByPosition() {
        BikeRequest bikeRequest = new BikeRequest(1.0, 2.0, 1);

        Bike bike1 = new Bike();
        bike1.setIdBike(1);
        bike1.setPositionX(2.0);
        bike1.setPositionY(3.0);
        bike1.setBooked(false);

        Bike bike2 = new Bike();
        bike2.setIdBike(2);
        bike2.setPositionX(4.0);
        bike2.setPositionY(5.0);
        bike2.setBooked(false);

        Bike bike3 = new Bike();
        bike3.setIdBike(1);
        bike3.setPositionX(2.0);
        bike3.setPositionY(3.0);
        bike3.setBooked(true);

        when(bikeDAO.findAllAvailable()).thenReturn(Arrays.asList(bike1, bike2, bike3));

        Bike result = bikeService.nextBikeAvailableByPosition(bikeRequest);
        assertNotNull(result);
        assertEquals(1, result.getIdBike());
        assertEquals(2.0, result.getPositionX());
        assertEquals(3.0, result.getPositionY());

        verify(bikeDAO).findAllAvailable();
    }

    @Test
    void testFindNearestZone() {
        Bike bike = new Bike();
        bike.setIdBike(1);
        bike.setPositionX(3.0);
        bike.setPositionY(4.0);

        Zone zone1 = new Zone();
        zone1.setId(1L);
        zone1.setLatitudePoint1(2.0);
        zone1.setLongitudePoint1(3.0);
        zone1.setLatitudePoint2(4.0);
        zone1.setLongitudePoint2(5.0);

        when(zoneDAO.findAllZones()).thenReturn(Arrays.asList(zone1));

        Zone result = bikeService.findNearestZone(bike);

        assertNotNull(result);
        assertEquals(1, result.getId());

        verify(zoneDAO).findAllZones();
    }

}