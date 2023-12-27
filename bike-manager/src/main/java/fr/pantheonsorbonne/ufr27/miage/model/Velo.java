package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;


@Entity
public class Velo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVelo", nullable = false)
	private int idVelo;

	@Column(name = "position", nullable = false, columnDefinition = "POINT")
	private Point position;

	@Column(name = "batterie", nullable = false)
	private int batterie;

	public int getId() {
		return idVelo;
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
