package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Velo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class VeloDAOImp implements VeloDAO {
    @Inject
    EntityManager em;

    @Override
    public Velo findById(int idVelo) {
        return em.find(Velo.class, idVelo);
    }
}
