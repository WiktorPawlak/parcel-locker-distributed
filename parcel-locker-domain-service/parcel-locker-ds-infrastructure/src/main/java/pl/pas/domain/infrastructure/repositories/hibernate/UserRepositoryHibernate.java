package pl.pas.domain.infrastructure.repositories.hibernate;

import static pl.pas.domain.infrastructure.repositories.hibernate.EntityManagerUtil.getEntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.domain.infrastructure.exceptions.RepositoryException;
import pl.pas.domain.infrastructure.model.user.ClientEntity;

@Repository
public class UserRepositoryHibernate extends HibernateRepository<ClientEntity> {

    public UserRepositoryHibernate() {
        super(ClientEntity.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(ClientEntity.class, id).setActive(false);
            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<ClientEntity> findByTelNumber(String telNumber) {
        return Optional.of((ClientEntity) getEntityManager()
            .createQuery("select u from ClientEntity u where u.telNumber = :telNumber")
            .setParameter("telNumber", telNumber)
            .getSingleResult());
    }

    public List<ClientEntity> findByTelNumberPart(String telNumberPart) {
        String wildCardTelNumber = "%" + telNumberPart + "%";
        return (List<ClientEntity>) getEntityManager()
            .createQuery("select u from ClientEntity u where u.telNumber like :wildCardTelNumber")
            .setParameter("wildCardTelNumber", wildCardTelNumber)
            .getResultList();
    }

    public Optional<ClientEntity> findUserById(final UUID uuid) {
        return Optional.of((ClientEntity) getEntityManager()
            .createQuery("select u from ClientEntity u where u.id = :uuid")
            .setParameter("uuid", uuid)
            .getSingleResult());
    }

}
