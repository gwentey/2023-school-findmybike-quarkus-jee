package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Manager;

public interface ManagerDAO {
    Manager findById(String idManager);
}
