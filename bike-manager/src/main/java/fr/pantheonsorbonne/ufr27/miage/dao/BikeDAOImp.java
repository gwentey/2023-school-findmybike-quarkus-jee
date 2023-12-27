package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class BikeDAOImp implements BikeDAO {
    @Inject
    EntityManager em;

    @Override
    public Bike findById(int idBike) {
        return em.find(Bike.class, idBike);
    }
}
