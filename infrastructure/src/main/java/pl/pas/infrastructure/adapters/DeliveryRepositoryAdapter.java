package pl.pas.infrastructure.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.model.user.ClientEnt;
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
    public List<Delivery> findReceivedByClient(Client user) {
        return null;
    }

    @Override
    public List<Delivery> findCurrentByClient(Client user) {
        return null;
    }

    @Override
    public List<Delivery> findByUser(Client user) {
        return null;
    }

}
