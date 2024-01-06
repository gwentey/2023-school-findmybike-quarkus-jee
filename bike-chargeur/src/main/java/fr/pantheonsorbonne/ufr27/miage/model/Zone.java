package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Entity
public class Zone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "latitude_point_1")
	private double latitudePoint1;

	@Column(name = "longitude_point_1")
	private double longitudePoint1;

	@Column(name = "latitude_point_2")
	private double latitudePoint2;

	@Column(name = "longitude_point_2")
	private double longitudePoint2;

	@Column(name = "latitude_point_3")
	private double latitudePoint3;

	@Column(name = "longitude_point_3")
	private double longitudePoint3;

	@Column(name = "latitude_point_4")
	private double latitudePoint4;

	@Column(name = "longitude_point_4")
	private double longitudePoint4;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLatitudePoint1() {
		return latitudePoint1;
	}

	public void setLatitudePoint1(double latitudePoint1) {
		this.latitudePoint1 = latitudePoint1;
	}

	public double getLongitudePoint1() {
		return longitudePoint1;
	}

	public void setLongitudePoint1(double longitudePoint1) {
		this.longitudePoint1 = longitudePoint1;
	}

	public double getLatitudePoint2() {
		return latitudePoint2;
	}

	public void setLatitudePoint2(double latitudePoint2) {
		this.latitudePoint2 = latitudePoint2;
	}

	public double getLongitudePoint2() {
		return longitudePoint2;
	}

	public void setLongitudePoint2(double longitudePoint2) {
		this.longitudePoint2 = longitudePoint2;
	}

	public double getLatitudePoint3() {
		return latitudePoint3;
	}

	public void setLatitudePoint3(double latitudePoint3) {
		this.latitudePoint3 = latitudePoint3;
	}

	public double getLongitudePoint3() {
		return longitudePoint3;
	}

	public void setLongitudePoint3(double longitudePoint3) {
		this.longitudePoint3 = longitudePoint3;
	}

	public double getLatitudePoint4() {
		return latitudePoint4;
	}

	public void setLatitudePoint4(double latitudePoint4) {
		this.latitudePoint4 = latitudePoint4;
	}

	public double getLongitudePoint4() {
		return longitudePoint4;
	}

	public void setLongitudePoint4(double longitudePoint4) {
		this.longitudePoint4 = longitudePoint4;
	}
}
