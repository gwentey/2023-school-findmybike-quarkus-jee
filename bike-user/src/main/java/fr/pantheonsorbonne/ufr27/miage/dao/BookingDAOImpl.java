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
	public Booking save(Bike bike, User user) {
		Booking booking = new Booking();
		booking.setBike(bike);
		booking.setUser(user);
		em.persist(booking);
		return booking;
	}

	/*
	@Override
	@Transactional
	public Ticket findTicket(int transitionalTicketId) throws NoSuchTicketException {
		Ticket t = em.find(Ticket.class, transitionalTicketId);
		if (null == t) {
			throw new NoSuchTicketException();
		}
		return t;
	}

	@Override
	@Transactional
	public Ticket emitTicketForCustomer(int transitionalTickerId, Customer customer) {
		Ticket t = em.find(Ticket.class, transitionalTickerId);
		t.setIdCustomer(customer);
		return t;

	}

	@Override
	@Transactional
	public void removeTransitionalTicket(int transitionalTicketId) {
		Ticket t = em.find(Ticket.class, transitionalTicketId);
		if (t != null) {
			em.remove(t);
		}
	}

	@Override
	@Transactional
	public Ticket save(Instant plus, Vendor vendor, Venue venue) {
		Ticket ticket = new Ticket();
		ticket.setValidUntil(plus);
		ticket.setIdVendor(vendor);
		ticket.setIdVenue(venue);
		em.persist(ticket);
		return ticket;
	}

	@Override
	public Ticket save(Instant plus, Vendor vendor, Venue venue, String seatReference) {
		Ticket tiket = this.save(plus, vendor, venue);
		tiket.setSeatReference(seatReference);
		return tiket;
	}

*/
}

