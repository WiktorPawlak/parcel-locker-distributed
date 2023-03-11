package pl.pas.infrastructure.repositories.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.core.applicationmodel.exceptions.RepositoryException;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.user.User;
import pl.pas.ports.outcoming.DeliveryRepository;

import java.util.List;
import java.util.UUID;

import static pl.pas.infrastructure.repositories.hibernate.EntityManagerUtil.getEntityManager;


public class DeliveryRepositoryHibernate extends HibernateRepository<Delivery> implements DeliveryRepository {

    public DeliveryRepositoryHibernate() {
        super(Delivery.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();

            Delivery delivery = entityManager.find(Delivery.class, id);
            delivery.setArchived(true);

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public List<Delivery> findByUser(User user) {
        return findBy(delivery -> delivery.getReceiver().equals(user));
    }

    @Override
    public List<Delivery> findReceivedByClient(User user) {
        return findBy(delivery -> delivery.getReceiver().equals(user) && delivery.isArchived());
    }

    @Override
    public List<Delivery> findCurrentByClient(User user) {
        return findBy(delivery -> delivery.getReceiver().equals(user) && !delivery.isArchived());
    }
}
