package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;

import java.util.List;

public interface BikeDAO {
    Bike findById(int idBike);
    List<Bike> findAll();
    Bike save(Bike bike);
    Bike merge(Bike bike);
    List<Bike> findAllAvailable();
    Bike createBike(Bike b);
    void deleteBike(int bikeId);
    Bike updateBike(int bikeId, Bike bikeDetails);

}
