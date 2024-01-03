package fr.pantheonsorbonne.ufr27.miage.startup;

import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;


@Singleton
public class Startup {
	@Transactional
	public void loadUsers(@Observes StartupEvent evt) {
		User.deleteAll();
		User.add("anthony","anthonypass", "rodrigues" ,"anthony", "user");
		User.add("louis","louispass", "bibal" ,"louis", "user");
	}
}