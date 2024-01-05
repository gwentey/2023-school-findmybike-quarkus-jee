package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

	@Override
	@Transactional
	public Booking findBookingByUserIdAndBikeId(long userId, int bikeId) {
		try {
			return em.createQuery("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.bike.idBike = :bikeId", Booking.class)
					.setParameter("userId", userId)
					.setParameter("bikeId", bikeId)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new RuntimeException("Aucun vélo n'est réservé par cet utilisateur");
		}
	}

	@Override
	@Transactional
	public void deleteBookingByUserIdAndBikeId(long userId, int bikeId) {
		Booking booking = findBookingByUserIdAndBikeId(userId, bikeId);
		em.remove(booking);
	}


}

