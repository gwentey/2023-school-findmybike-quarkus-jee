package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(name = "mysql")
    EntityManager em;

    @Transactional
    public User findByPseudo(String pseudo) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.pseudo = :pseudo", User.class)
                    .setParameter("pseudo", pseudo)
                    .getSingleResult();
            return user;
        } catch (Exception e) {
            // Gérer l'exception ou logger l'erreur
            return null;
        }
    }
}
