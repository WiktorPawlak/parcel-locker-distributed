package pl.pas.ports.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.user.User;
import pl.pas.infrastructure.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.ports.outcoming.DeliveryRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DeliveryRepositoryAdapter implements DeliveryRepository {

    @Inject
    private DeliveryRepositoryHibernate deliveryRepository;

    @Override
    public Delivery get(UUID deliveryId) {
        return null;
    }

    @Override
    public void add(Delivery delivery) {

    }

    @Override
    public void update(Delivery delivery) {

    }

    @Override
    public List<Delivery> findAll() {
        return null;
    }

    @Override
    public List<Delivery> findReceivedByClient(User user) {
        return null;
    }

    @Override
    public List<Delivery> findCurrentByClient(User user) {
        return null;
    }

    @Override
    public List<Delivery> findByUser(User user) {
        return null;
    }
}
