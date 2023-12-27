package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBooking", nullable = false)
	private int idBooking;

	@ManyToOne
	private Bike bike;
	@ManyToOne
	private User user;


	public int getIdBooking() {
		return idBooking;
	}

	public Bike getBike() {
		return bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
