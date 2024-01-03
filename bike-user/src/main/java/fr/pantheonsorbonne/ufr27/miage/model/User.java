package fr.pantheonsorbonne.ufr27.miage.model;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

@Entity
@UserDefinition
public class User extends PanacheEntity {

	@Column(name = "nom", nullable = false)
	private String nom;
	@Column(name = "prenom", nullable = false)
	private String prenom;
	@Username
	private String username;
	@Password
	private String password;
	@Roles
	public String role;


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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Adds a new user to the database
	 * @param username the username
	 * @param password the unencrypted password (it will be encrypted with bcrypt)
	 * @param role the comma-separated roles
	 */
	public static void add(String username, String password, String nom, String prenom, String role) {
		User user = new User();
		user.prenom = prenom;
		user.nom = nom;
		user.username = username;
		user.password = BcryptUtil.bcryptHash(password);
		user.role = role;
		user.persist();
	}

}
