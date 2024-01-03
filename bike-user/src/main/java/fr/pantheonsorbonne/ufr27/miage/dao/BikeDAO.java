package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;

public interface BikeDAO {
    Bike findById(int idBike);

    Bike save(Bike Bike);

    Bike merge(Bike bike);

}
