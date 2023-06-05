package pl.pas.domain.ports.incoming;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import pl.pas.domain.core.applicationmodel.model.delivery.Delivery;
import pl.pas.domain.core.applicationmodel.model.user.Client;

public interface DeliveryService {
    Delivery makeParcelDelivery(
        BigDecimal basePrice,
        double width,
        double height,
        double length,
        double weight,
        boolean isFragile,
        String shipperTel,
        String receiverTel,
        String lockerId);

    Delivery makeListDelivery(
        BigDecimal basePrice,
        boolean isPriority,
        String shipperTel,
        String receiverTel,
        String lockerId);

    void putInLocker(UUID deliveryId, String lockerId, String accessCode);

    void takeOutDelivery(UUID deliveryId, String receiverTel, String accessCode);

    BigDecimal checkClientShipmentBalance(Client user);

    List<Delivery> getAllClientDeliveries(Client user);

    List<Delivery> getAllCurrentClientDeliveries(String telNumber);

    List<Delivery> getAllReceivedClientDeliveries(String telNumber);

    Delivery getDelivery(UUID id);
}
