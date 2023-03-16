package pl.pas.ports.outcoming;

import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.user.Client;

import java.util.List;
import java.util.UUID;


public interface DeliveryRepository {
    Delivery get(UUID deliveryId);

    void add(Delivery delivery);

    void update(Delivery delivery);

    List<Delivery> findAll();
    List<Delivery> findReceivedByClient(Client user);
    List<Delivery> findCurrentByClient(Client user);

    List<Delivery> findByUser(Client user);

}
