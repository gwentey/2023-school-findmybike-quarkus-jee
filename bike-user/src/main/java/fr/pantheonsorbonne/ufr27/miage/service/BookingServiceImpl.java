package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class BookingServiceImpl implements BookingService {

	@Inject
	BookingDAO bookingDAO;

	@PersistenceContext
	EntityManager em;

	@Override
	public Booking book(Booking b) {
		Booking booking = bookingDAO.save(b.getBike(), b.getUser());
		return booking;
	}

}
