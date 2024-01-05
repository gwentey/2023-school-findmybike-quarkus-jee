package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.User;

public interface UserDAO {

	User findById(long idUser);
	User findByUsername(String username);
	void add(Long id, String username, String password, String nom, String prenom, String role);
}
