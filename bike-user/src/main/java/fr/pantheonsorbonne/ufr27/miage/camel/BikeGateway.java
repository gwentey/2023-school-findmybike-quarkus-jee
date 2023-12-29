package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.BikeRequest;


public interface BikeGateway {

        void nextBikeAvailableByPosition(Double positionX, Double positionY);
}

