package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class BikeServiceImpl implements BikeService {

	@Inject
	BikeDAO bikeDAO;

	@PersistenceContext
	EntityManager em;

	@Override
	public void nextBikeAvailableByPosition(Double positionX, Double positionY) {
		//        List<String> ticketForVenue = em.createQuery("SELECT t.seatReference from Ticket t where t.idVenue.id=:venueId and t.seatReference is not null").setParameter("venueId", venueId).getResultList();
		Log.info("message nextBikeAvailableByPosition recu v2");
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
