package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Manager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ManagerDAOImp implements ManagerDAO {
    @Inject
    EntityManager em;

    @Override
    public Manager findById(String idManager) {
        return em.find(Manager.class, idManager);
    }
}
