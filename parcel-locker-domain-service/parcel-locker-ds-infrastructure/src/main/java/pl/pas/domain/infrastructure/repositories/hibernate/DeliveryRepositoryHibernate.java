package pl.pas.domain.infrastructure.repositories.hibernate;

import static pl.pas.domain.infrastructure.repositories.hibernate.EntityManagerUtil.getEntityManager;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.domain.infrastructure.exceptions.RepositoryException;
import pl.pas.domain.infrastructure.model.delivery.DeliveryEntity;
import pl.pas.domain.infrastructure.model.user.ClientEntity;

@Repository
public class DeliveryRepositoryHibernate extends HibernateRepository<DeliveryEntity> {

    public DeliveryRepositoryHibernate() {
        super(DeliveryEntity.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();

            DeliveryEntity delivery = entityManager.find(DeliveryEntity.class, id);
            delivery.setArchived(true);

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public List<DeliveryEntity> findByUser(ClientEntity user) {
        return findBy(delivery -> delivery.getReceiver().equals(user));
    }

    public List<DeliveryEntity> findReceivedByClient(ClientEntity user) {
        return findBy(delivery -> delivery.getReceiver().equals(user) && delivery.isArchived());
    }

    public List<DeliveryEntity> findCurrentByClient(ClientEntity user) {
        return findBy(delivery -> delivery.getReceiver().equals(user) && !delivery.isArchived());
    }
}
