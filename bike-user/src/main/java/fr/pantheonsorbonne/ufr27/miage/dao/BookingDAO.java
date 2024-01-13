package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.exception.NoBookingException;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.transaction.Transactional;

public interface BookingDAO {

	Booking save(Booking booking);
	boolean isBikeBooked(int bikeId);

	Booking findBookingByUserIdAndBikeId(long userId, int bikeId) throws NoBookingException.NoBookingUserIDBikeID;

	void deleteBookingByUserIdAndBikeId(long userId, int bikeId) throws NoBookingException.NoBookingUserIDBikeID;
}
