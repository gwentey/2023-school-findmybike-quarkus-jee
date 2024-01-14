package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.exception.NoBikeFound;
import fr.pantheonsorbonne.ufr27.miage.exception.NoZoneFound;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
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
        return em.createQuery("SELECT b FROM Bike b WHERE b.booked = false AND b.inCharge = false", Bike.class)
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


    @Override
    @Transactional
    public void deleteBike(int bikeId) {
        Bike bike = em.find(Bike.class, bikeId);
        if (bike != null) {
            em.remove(bike);
        } else {
            throw new NoBikeFound.NoBikeFoundByID(bikeId);
        }
    }

    @Override
    @Transactional
    public Bike createBike(Bike b) {
        Zone zone = em.find(Zone.class, b.getZone().getId());

        Bike bike = new Bike();
        bike.setManagerId(b.getManagerId());
        bike.setPositionX(b.getPositionX());
        bike.setPositionY(b.getPositionY());
        bike.setBatterie(b.getBatterie());
        bike.setBooked(false);
        bike.setInCharge(false);
        if(zone != null){
            bike.setZone(zone);
        } else {
            throw new NoZoneFound.NoZoneFoundById(b.getZone().getId());
        }

        return save(bike);
    }

    @Override
    @Transactional
    public Bike updateBike(int bikeId, Bike bikeDetails) {
        Zone zone = em.find(Zone.class, bikeDetails.getZone().getId());
        Bike bike = em.find(Bike.class, bikeId);
        if (bike != null) {
            bike.setPositionX(bikeDetails.getPositionX());
            bike.setPositionY(bikeDetails.getPositionY());
            bike.setBatterie(bikeDetails.getBatterie());
            bike.setManagerId(bikeDetails.getManagerId());

            if(zone != null){
                bike.setZone(zone);
            } else {
                throw new NoZoneFound.NoZoneFoundById(bikeDetails.getZone().getId());
            }

            return em.merge(bike);
        } else {
            throw new NoBikeFound.NoBikeFoundByID(bikeId);
        }
    }
}

