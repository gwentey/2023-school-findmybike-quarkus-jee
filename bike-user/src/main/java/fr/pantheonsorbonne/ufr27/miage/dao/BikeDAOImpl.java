package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BikeDAOImpl implements BikeDAO {
    @PersistenceContext(name = "mysql")
    EntityManager em;

    @Override
    @Transactional
    public Bike findById(int idBike) {
        return em.find(Bike.class, idBike);
    }
}
