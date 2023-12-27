package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;

public interface BikeDAO {
    Bike findById(int idVelo);
}
