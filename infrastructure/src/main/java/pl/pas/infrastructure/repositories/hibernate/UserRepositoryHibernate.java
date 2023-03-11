package pl.pas.infrastructure.repositories.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.infrastructure.exceptions.RepositoryException;
import pl.pas.infrastructure.model.user.UserEnt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.pas.infrastructure.repositories.hibernate.EntityManagerUtil.getEntityManager;

public class UserRepositoryHibernate extends HibernateRepository<UserEnt> {

    public UserRepositoryHibernate() {
        super(UserEnt.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(UserEnt.class, id).setActive(false);
            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<UserEnt> findByTelNumber(String telNumber) {
        return Optional.of((UserEnt) getEntityManager()
            .createQuery("select u from UserEnt u where u.telNumber = :telNumber")
            .setParameter("telNumber", telNumber)
            .getSingleResult());
    }

    public List<UserEnt> findByTelNumberPart(String telNumberPart) {
        String wildCardTelNumber = "%" + telNumberPart + "%";
        return (List<UserEnt>) getEntityManager()
            .createQuery("select u from UserEnt u where u.telNumber like :wildCardTelNumber")
            .setParameter("wildCardTelNumber", wildCardTelNumber)
            .getResultList();
    }

    @Override
    public Optional<UserEnt> findUserById(final UUID uuid) {
        return Optional.of((UserEnt) getEntityManager()
            .createQuery("select u from UserEnt u where u.id = :uuid")
            .setParameter("uuid", uuid)
            .getSingleResult());
    }

}
