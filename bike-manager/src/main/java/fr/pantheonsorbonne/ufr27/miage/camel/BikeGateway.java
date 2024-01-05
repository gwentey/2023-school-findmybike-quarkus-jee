package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;


public interface BikeGateway {
        Bike nextBikeAvailableByPosition(BikeRequest bikeRequest);
        Bike getABikeById(int idBike);
        boolean bookBikeById(int bikeId);
        boolean dropABike(Bike bike);
        void sendABikeInCharge(Bike bike);
        boolean setInCharge(Bike bike);
        boolean isCharged(Bike bike);

}

