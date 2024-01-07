package fr.pantheonsorbonne.ufr27.miage.startup;

import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;


@Singleton
public class Startup {
	@Inject
	UserDAO userDAO;
	@Transactional
	public void loadUsers(@Observes StartupEvent evt) {
		userDAO.add(1L,"anthony","anthonypass", "rodrigues" ,"anthony", "user");
		userDAO.add(2L,"louis","louispass", "bibal" ,"louis", "user");
		userDAO.add(3L,"paul","paulpass", "adnet" ,"paul", "user");
		userDAO.add(4L,"jaho","jahopass", "mvenge" ,"jaho", "user");
	}
}