package fr.pantheonsorbonne.ufr27.miage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Bike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBike", nullable = false)
	@JsonProperty("idBike")
	private int idBike;

	@Column(name = "positionY", nullable = false)
	private Double positionY;
	@Column(name = "positionX", nullable = false)
	private Double positionX;

	@Column(name = "batterie", nullable = false)
	private int batterie;

	@Column(name = "managerId", nullable = false)
	private int managerId;

	@Column(name = "booked", nullable = false)
	@ColumnDefault("false")
	private Boolean booked;

	@Column(name = "inCharge", nullable = false)
	@ColumnDefault("false")
	private Boolean inCharge;

	@ManyToOne
	@JoinColumn(name = "zone_id", referencedColumnName = "id", nullable = true)
	private Zone zone;

	public int getIdBike() {
		return idBike;
	}

	public void setIdBike(int idBike) {
		this.idBike = idBike;
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

	public int getBatterie() {
		return batterie;
	}

	public void setBatterie(int batterie) {
		this.batterie = batterie;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public Boolean getBooked() {
		return booked;
	}

	public void setBooked(Boolean booked) {
		this.booked = booked;
	}

	public Boolean getInCharge() {
		return inCharge;
	}

	public void setInCharge(Boolean inCharge) {
		this.inCharge = inCharge;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
}
