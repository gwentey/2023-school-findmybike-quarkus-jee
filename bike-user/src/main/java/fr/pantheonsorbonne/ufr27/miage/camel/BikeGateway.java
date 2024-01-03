package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;


public interface BikeGateway {

        Bike nextBikeAvailableByPosition(Double positionX, Double positionY);

        Bike getABikeById(int idBike);

}

