package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
public class Bike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBike", nullable = false)
	private int idBike;

	@Column(name = "positionY", nullable = false)
	private Double positionY;
	@Column(name = "positionX", nullable = false)
	private Double positionX;

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

	public Double getPositionY() {
		return positionY;
	}

	public void setPositionY(Double positionY) {
		this.positionY = positionY;
	}

	public Double getPositionX() {
		return positionX;
	}

	public void setPositionX(Double positionX) {
		this.positionX = positionX;
	}
}
