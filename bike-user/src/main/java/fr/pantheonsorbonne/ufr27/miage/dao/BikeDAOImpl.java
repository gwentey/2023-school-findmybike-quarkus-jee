package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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

    @Transactional
    public void persistWithExistingId(Bike bike) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.createNativeQuery("INSERT INTO Bike (idBike, batterie, positionX, positionY, managerId) VALUES (?, ?, ?, ?, ?)")
                    .setParameter(1, bike.getIdBike())
                    .setParameter(2, bike.getBatterie())
                    .setParameter(3, bike.getPositionX())
                    .setParameter(4, bike.getPositionY())
                    .setParameter(5, bike.getManagerId())
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
