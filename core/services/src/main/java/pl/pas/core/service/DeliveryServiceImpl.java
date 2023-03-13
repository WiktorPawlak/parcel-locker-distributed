package pl.pas.core.service;

import static pl.pas.core.applicationmodel.model.delivery.DeliveryStatus.READY_TO_PICKUP;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.exceptions.DeliveryManagerException;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.delivery.DeliveryStatus;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.ports.incoming.DeliveryService;
import pl.pas.ports.outcoming.DeliveryRepository;
import pl.pas.ports.outcoming.LockerRepository;
import pl.pas.ports.outcoming.UserRepository;

@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    @Inject
    private DeliveryRepository deliveryRepository;
    @Inject
    private LockerRepository lockerRepository;
    @Inject
    private UserRepository userRepository;

    @Override
    public synchronized Delivery makeParcelDelivery(
        BigDecimal basePrice,
        double width,
        double height,
        double length,
        double weight,
        boolean isFragile,
        String shipperTel,
        String receiverTel,
        String lockerId) {
        Client shipper =
            (Client) userRepository
                .findByTelNumber(shipperTel) //todo po pobraniu encji jest ona konwertowana do domeny - tracimy wersje
                .orElseThrow();

        Client receiver =
            (Client) userRepository
                .findByTelNumber(receiverTel)
                .orElseThrow();

        Locker locker =
            lockerRepository
                .findByIdentityNumber(lockerId)
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));

        Delivery delivery =
            new Delivery(
                basePrice, width, height, length, weight, isFragile, shipper, receiver, locker);
        deliveryRepository.add(delivery);
        return delivery;
    }

    @Override
    public synchronized Delivery makeListDelivery(
        BigDecimal basePrice,
        boolean isPriority,
        String shipperTel,
        String receiverTel,
        String lockerId) {
        Client shipper =
            (Client) userRepository
                .findByTelNumber(shipperTel)
                .orElseThrow();

        Client receiver =
            (Client) userRepository
                .findByTelNumber(receiverTel)
                .orElseThrow();

        Locker locker =
            lockerRepository
                .findByIdentityNumber(lockerId)
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));

        Delivery delivery = new Delivery(basePrice, isPriority, shipper, receiver, locker);
        deliveryRepository.add(delivery);


        return delivery;
    }

    @Override
    public synchronized void putInLocker(UUID deliveryId, String lockerId, String accessCode) {
        Delivery latestDeliveryState = deliveryRepository.get(deliveryId);

        validateClient(latestDeliveryState.getReceiver());
        validateClient(latestDeliveryState.getShipper());
        validateDelivery(latestDeliveryState);

        Locker chosenLocker =
            lockerRepository
                .findByIdentityNumber(lockerId)
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));

        chosenLocker.putIn(
            latestDeliveryState, latestDeliveryState.getReceiver().getTelNumber(), accessCode);

        latestDeliveryState.setAllocationStart(LocalDateTime.now());
        latestDeliveryState.setStatus(READY_TO_PICKUP);
        lockerRepository.update(chosenLocker);
        deliveryRepository.update(latestDeliveryState);
    }

    @Override
    public synchronized void takeOutDelivery(UUID deliveryId, String receiverTel, String accessCode) {
        Delivery latestDeliveryState = deliveryRepository.get(deliveryId);

        Locker locker =
            lockerRepository
                .findByIdentityNumber(latestDeliveryState.getLocker().getIdentityNumber())
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));
        ;
        Delivery delivery = locker.takeOut(receiverTel, accessCode);

        if (delivery != null) {
            delivery.setAllocationStop(LocalDateTime.now());
            delivery.setStatus(DeliveryStatus.RECEIVED);
            delivery.setArchived(true);

            deliveryRepository.update(delivery);

        }
    }

    @Override
    public BigDecimal checkClientShipmentBalance(Client user) {
        BigDecimal balance = BigDecimal.ZERO;
        if (user == null) throw new DeliveryManagerException("User is a nullptr!");
        for (Delivery delivery : deliveryRepository.findAll()) {
            if (delivery.getShipper().equals(user)) balance = balance.add(delivery.getCost());
        }

        return balance;
    }

    @Override
    public List<Delivery> getAllClientDeliveries(Client user) {
        return deliveryRepository.findByUser(user);
    }

    @Override
    public List<Delivery> getAllCurrentClientDeliveries(String telNumber) {
        Client client =
            (Client) userRepository
                .findByTelNumber(telNumber)
                .orElseThrow(() -> new DeliveryManagerException("Client not found"));

        return deliveryRepository.findCurrentByClient(client);
    }

    @Override
    public List<Delivery> getAllReceivedClientDeliveries(String telNumber) {
        Client receiver =
            (Client) userRepository
                .findByTelNumber(telNumber)
                .orElseThrow(() -> new DeliveryManagerException("Receiver not found"));

        return deliveryRepository.findReceivedByClient(receiver);
    }

    @Override
    public Delivery getDelivery(UUID id) {
        return deliveryRepository.get(id);
    }

    private void validateClient(Client user) {
        if (!user.isActive()) throw new DeliveryManagerException("User account is inactive.");
    }

    private void validateDelivery(Delivery delivery) {
        if (delivery.getStatus() == READY_TO_PICKUP || delivery.isArchived())
            throw new DeliveryManagerException("Delivery is already in locker or is archived.");
    }
}
