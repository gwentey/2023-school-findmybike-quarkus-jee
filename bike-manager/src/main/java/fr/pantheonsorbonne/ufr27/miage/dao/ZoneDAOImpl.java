package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Zone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ZoneDAOImpl implements ZoneDAO {

	@PersistenceContext(name = "mysql")
	EntityManager em;

	@Override
	@Transactional
	public Zone findById(int idZone) {
		return em.find(Zone.class, idZone);
	}

	@Override
	@Transactional
	public List<Zone> findAllZones() {
		return em.createQuery("SELECT z FROM Zone z", Zone.class)
				.getResultList();
	}

}
