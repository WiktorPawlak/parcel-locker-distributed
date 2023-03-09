package pl.pas.parcellocker.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.user.User;

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

    BigDecimal checkClientShipmentBalance(User user);

    List<Delivery> getAllClientDeliveries(User user);

    List<Delivery> getAllCurrentClientDeliveries(String telNumber);

    List<Delivery> getAllReceivedClientDeliveries(String telNumber);

    Delivery getDelivery(UUID id);
}
