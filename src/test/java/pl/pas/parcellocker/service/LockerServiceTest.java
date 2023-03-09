package pl.pas.parcellocker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.User;

class LockerServiceTest extends TestsConfig {

    private final LockerService lockerService = new LockerServiceImpl(lockerRepository);

    @Test
    void Should_CreateLocker() {
        Locker locker = lockerService.createLocker("LDZ69", "Gawronska 9, Lodz 12-123", 10);
        assertEquals(lockerService.getLocker("LDZ69").getIdentityNumber(), locker.getIdentityNumber());
    }

    @Test
    void Should_RemoveLocker() {
        Locker locker = lockerService.createLocker("LDZ12", "Gawronska 9, Lodz 12-123", 10);
        lockerService.removeLocker("LDZ12");
        assertThrows(LockerManagerException.class, () -> lockerService.getLocker("LDZ12"));
    }

    @Test
    void Should_ThrowException_WhenThereIsAllocationOnLocker() {
        Locker locker = lockerService.createLocker("LDZ12", "Gawronska 9, Lodz 12-123", 10);

        DeliveryService deliveryService =
            new DeliveryServiceImpl(deliveryRepository, lockerRepository, clientRepository);
        User shipper1 = new Client("Oscar", "Trel", "321312312");
        User receiver1 = new Client("Bartosh", "Siekan", "123123123");
        clientRepository.add(shipper1);
        clientRepository.add(receiver1);
        Delivery delivery =
            deliveryService.makeParcelDelivery(
                BigDecimal.TEN,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());
        deliveryService.putInLocker(delivery.getId(), delivery.getLocker().getIdentityNumber(), "123");

        assertThrows(LockerManagerException.class, () -> lockerService.removeLocker("LDZ12"));

        deliveryService.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "123");
    }

    @Test
    void Should_ThrowException_WhenGivenLockerNameIsDuplicated() {
        lockerService.createLocker("LDZ11", "Gawronska 12, Lodz 12-123", 10);
        assertThrows(
            LockerManagerException.class,
            () -> lockerService.createLocker("LDZ11", "Gawronska 22, Lodz 12-123", 10));
    }
}
