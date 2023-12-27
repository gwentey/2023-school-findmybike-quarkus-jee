package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

import java.util.UUID;

public class User {

	private String idUser;
	private String nom;
	private String prenom;
	private String pseudo;
	private String password;

	public User(String nom, String prenom, String pseudo, String password) {
		this.idUser = UUID.randomUUID().toString();
		this.nom = nom;
		this.prenom = prenom;
		this.pseudo = pseudo;
		this.password = password;
	}

	public String getId() {
		return idUser;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
