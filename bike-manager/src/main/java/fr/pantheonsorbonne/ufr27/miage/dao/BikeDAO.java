package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;

import java.util.List;

public interface BikeDAO {
    Bike findById(int idBike);
    List<Bike> findAll();
}
