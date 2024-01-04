package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BookingDAOImpl implements BookingDAO {

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public Booking save(Booking booking) {
		em.persist(booking);
		return booking;
	}

	@Override
	@Transactional
	public boolean isBikeBooked(int bikeId) {
		Long count = em.createQuery("SELECT COUNT(b) FROM Booking b WHERE b.bike.idBike = :bikeId", Long.class)
				.setParameter("bikeId", bikeId)
				.getSingleResult();
		return count != null && count > 0;
	}

}

