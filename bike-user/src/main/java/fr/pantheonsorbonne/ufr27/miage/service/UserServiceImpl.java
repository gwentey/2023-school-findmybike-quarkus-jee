package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	BookingDAO bookingDAO;

	@Inject
	BikeGateway bikeGateway;

	@PersistenceContext
	EntityManager em;

	@Override
	public Booking book(int userId, int bikeId) {

		Bike bike = em.find(Bike.class, bikeId);
		System.out.println("BIKE DETECTED :" + bike);
		User user = em.find(User.class, userId);

		Booking booking = bookingDAO.save(bike, user);
		return booking;
	}

	@Override
	public Bike getABikeById(int idBike) {
		return em.find(Bike.class, idBike);
	}

	@Override
	public void nextBikeAvailableByPosition(Double positionX, Double positionY) {
		bikeGateway.nextBikeAvailableByPosition(positionX,positionY);
	}

	/*
	@Override
	@Transactional
	public Booking book(Booking booking) throws UnsuficientQuotaForVenueException {
		try {
			VenueQuota vq = venueQuotaDAO.getMatchingQuota(booking.getVendorId(), booking.getVenueId(), booking.getStandingTicketsNumber(), booking.getSeatingTicketsNumber());
			vq.setSeatingQuota(vq.getSeatingQuota() - booking.getSeatingTicketsNumber());
			vq.setStandingQuota(vq.getStandingQuota() - booking.getStandingTicketsNumber());

			Venue venue = venueDAO.findById(booking.getVenueId());
			Vendor vendor = vendorDAO.findById(booking.getVendorId());


			for (int i = 0; i < booking.getStandingTicketsNumber(); i++) {
				Ticket ticket = ticketDAO.save(Instant.now().plus(10, ChronoUnit.HOURS), vendor, venue);
				booking.getStandingTransitionalTicket().add(ticket.getId());
			}

			for (int i = 0; i < booking.getSeatingTicketsNumber(); i++) {
				Ticket ticket = ticketDAO.save(Instant.now().plus(10, ChronoUnit.MINUTES), vendor, venue);
				booking.getSeatingTransitionalTicket().add(ticket.getId());
			}
		} catch (NonUniqueResultException | NoResultException e) {
			throw new UnsuficientQuotaForVenueException(booking.getVenueId());
		}
		return booking;
	}
	 */
}
