package pl.pas.domain.ports.outcoming;

import java.util.List;
import java.util.UUID;

import pl.pas.domain.core.applicationmodel.model.delivery.Delivery;
import pl.pas.domain.core.applicationmodel.model.user.Client;


public interface DeliveryRepository {
    Delivery get(UUID deliveryId);

    void add(Delivery delivery);

    void update(Delivery delivery);

    List<Delivery> findAll();

    List<Delivery> findReceivedByClient(Client user);

    List<Delivery> findCurrentByClient(Client user);

    List<Delivery> findByUser(Client user);

}
