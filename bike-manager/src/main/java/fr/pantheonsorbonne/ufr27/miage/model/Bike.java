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
	private BigDecimal positionY;
	@Column(name = "positionX", nullable = false)
	private BigDecimal positionX;

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

	public BigDecimal getPositionY() {
		return positionY;
	}

	public void setPositionY(BigDecimal positionY) {
		this.positionY = positionY;
	}

	public BigDecimal getPositionX() {
		return positionX;
	}

	public void setPositionX(BigDecimal positionX) {
		this.positionX = positionX;
	}
}
