package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUser", nullable = false)
	private int idUser;
	@Column(name = "nom", nullable = false)

	private String nom;
	@Column(name = "prenom", nullable = false)

	private String prenom;
	@Column(name = "pseudo", nullable = false)

	private String pseudo;
	@Column(name = "password", nullable = false)

	private String password;


	public int getId() {
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
