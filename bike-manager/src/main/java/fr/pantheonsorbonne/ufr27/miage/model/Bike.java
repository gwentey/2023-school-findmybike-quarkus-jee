package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;


@Entity
public class Bike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBike", nullable = false)
	private int idBike;

	@Column(name = "position", nullable = false, columnDefinition = "POINT")
	private Point position;

	@Column(name = "batterie", nullable = false)
	private int batterie;

	@Column(name = "managerId", nullable = false)
	private int managerId;

	public int getId() {
		return idBike;
	}

	public int getBatterie() {
		return batterie;
	}

	public void setBatterie(int batterie) {
		batterie = batterie;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
