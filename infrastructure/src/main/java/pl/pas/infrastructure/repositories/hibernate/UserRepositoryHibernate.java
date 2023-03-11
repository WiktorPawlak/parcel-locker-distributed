package pl.pas.infrastructure.repositories.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.core.applicationmodel.exceptions.RepositoryException;
import pl.pas.core.applicationmodel.model.user.User;
import pl.pas.ports.outcoming.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.pas.infrastructure.repositories.hibernate.EntityManagerUtil.getEntityManager;

public class UserRepositoryHibernate extends HibernateRepository<User> implements UserRepository {

    public UserRepositoryHibernate() {
        super(User.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(User.class, id).setActive(false);
            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<User> findByTelNumber(String telNumber) {
        return Optional.of((User) getEntityManager()
            .createQuery("select u from User u where u.telNumber = :telNumber")
            .setParameter("telNumber", telNumber)
            .getSingleResult());
    }

    public List<User> findByTelNumberPart(String telNumberPart) {
        String wildCardTelNumber = "%" + telNumberPart + "%";
        return (List<User>) getEntityManager()
            .createQuery("select u from User u where u.telNumber like :wildCardTelNumber")
            .setParameter("wildCardTelNumber", wildCardTelNumber)
            .getResultList();
    }

    @Override
    public Optional<User> findUserById(final UUID uuid) {
        return Optional.of((User) getEntityManager()
            .createQuery("select u from User u where u.id = :uuid")
            .setParameter("uuid", uuid)
            .getSingleResult());
    }

}
