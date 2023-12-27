package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

import java.util.UUID;

public class Manager {

	private String idManager;
	private String nom;

	public Manager() {
	}

	public Manager(String nom) {
		this.idManager = UUID.randomUUID().toString();
		this.nom = nom;
	}

	public String getId() {
		return idManager;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
