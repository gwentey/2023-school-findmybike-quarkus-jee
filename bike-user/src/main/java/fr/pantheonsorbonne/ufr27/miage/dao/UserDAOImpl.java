package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.exception.NoUserFound;
import fr.pantheonsorbonne.ufr27.miage.exception.NoUserFound.NoUserFoundByUsername;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.NewCookie;

import java.util.Optional;

@ApplicationScoped
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(name = "mysql")
    EntityManager em;

    @Override
    @Transactional
    public User findById(long idUser) {
        return em.find(User.class, idUser);
    }

    @Override
    public User findByUsername(String username) throws NoUserFound.NoUserFoundByUsername {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoUserFound.NoUserFoundByUsername e) {
            e.printMessage();
            throw e;
        }
    }

    @Transactional
    public  User save(User user) {
        em.merge(user);
        return user;
    }

    /**
     * Ajoute un nouvel utilisateur à la base de données
     * @param username le nom d'utilisateur
     * @param password le mot de passe non chiffré (il sera chiffré avec bcrypt)
     * @param role les rôles séparés par des virgules
     */
    public void add(Long id, String username, String password, String nom, String prenom, String role) {
        User user = new User();
        user.id = id;
        user.setPrenom(prenom);
        user.setNom(nom);
        user.setUsername(username);
        user.setPassword(BcryptUtil.bcryptHash(password));
        user.role = role;
        save(user);
    }
}
