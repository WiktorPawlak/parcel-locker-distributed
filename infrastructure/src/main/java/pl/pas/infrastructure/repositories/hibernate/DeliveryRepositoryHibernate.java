package pl.pas.infrastructure.repositories.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.infrastructure.exceptions.RepositoryException;
import pl.pas.infrastructure.model.delivery.DeliveryEnt;
import pl.pas.infrastructure.model.user.UserEnt;

import java.util.List;
import java.util.UUID;

import static pl.pas.infrastructure.repositories.hibernate.EntityManagerUtil.getEntityManager;


public class DeliveryRepositoryHibernate extends HibernateRepository<DeliveryEnt> {

    public DeliveryRepositoryHibernate() {
        super(DeliveryEnt.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();

            DeliveryEnt delivery = entityManager.find(DeliveryEnt.class, id);
            delivery.setArchived(true);

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public List<DeliveryEnt> findByUser(UserEnt user) {
        return findBy(delivery -> delivery.getReceiver().equals(user));
    }

    @Override
    public List<DeliveryEnt> findReceivedByClient(UserEnt user) {
        return findBy(delivery -> delivery.getReceiver().equals(user) && delivery.isArchived());
    }

    @Override
    public List<DeliveryEnt> findCurrentByClient(UserEnt user) {
        return findBy(delivery -> delivery.getReceiver().equals(user) && !delivery.isArchived());
    }
}
