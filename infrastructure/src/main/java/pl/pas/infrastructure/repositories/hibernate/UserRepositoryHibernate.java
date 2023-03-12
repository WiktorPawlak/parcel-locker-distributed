package pl.pas.infrastructure.repositories.hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.infrastructure.exceptions.RepositoryException;
import pl.pas.infrastructure.model.user.ClientEnt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.pas.infrastructure.repositories.hibernate.EntityManagerUtil.getEntityManager;

@ApplicationScoped
public class UserRepositoryHibernate extends HibernateRepository<ClientEnt> {

    public UserRepositoryHibernate() {
        super(ClientEnt.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(ClientEnt.class, id).setActive(false);
            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<ClientEnt> findByTelNumber(String telNumber) {
        return Optional.of((ClientEnt) getEntityManager()
            .createQuery("select u from ClientEnt u where u.telNumber = :telNumber")
            .setParameter("telNumber", telNumber)
            .getSingleResult());
    }

    public List<ClientEnt> findByTelNumberPart(String telNumberPart) {
        String wildCardTelNumber = "%" + telNumberPart + "%";
        return (List<ClientEnt>) getEntityManager()
            .createQuery("select u from ClientEnt u where u.telNumber like :wildCardTelNumber")
            .setParameter("wildCardTelNumber", wildCardTelNumber)
            .getResultList();
    }

    public Optional<ClientEnt> findUserById(final UUID uuid) {
        return Optional.of((ClientEnt) getEntityManager()
            .createQuery("select u from ClientEnt u where u.id = :uuid")
            .setParameter("uuid", uuid)
            .getSingleResult());
    }

}
