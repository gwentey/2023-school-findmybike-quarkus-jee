package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BikeDAOImpl implements BikeDAO {
    @PersistenceContext(name = "mysql")
    EntityManager em;

    @Override
    @Transactional
    public Bike findById(int idBike) {
        return em.find(Bike.class, idBike);
    }

    @Override
    @Transactional
    public List<Bike> findAll() {
        return em.createQuery("SELECT b FROM Bike b", Bike.class)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Bike> findAllAvailable() {
        return em.createQuery("SELECT b FROM Bike b WHERE b.booked = false", Bike.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Bike save(Bike bike) {
        em.persist(bike);
        return bike;
    }

    @Override
    @Transactional
    public Bike merge(Bike bike) {
        return em.merge(bike);
    }
}
