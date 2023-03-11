package pl.pas.core.service;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.pas.core.applicationmodel.exceptions.DeliveryManagerException;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.core.applicationmodel.model.user.User;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryServiceTest extends TestsConfig {

    private final DeliveryService deliveryService =
        new DeliveryServiceImpl(deliveryRepository, lockerRepository, clientRepository);
    private final BigDecimal basePrice = BigDecimal.TEN;
    private User shipper1;
    private User receiver1;
    private Locker locker;

    @BeforeAll
    void setup() {
        locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 20);
        shipper1 = new Client("Oscar", "Trel", "321312312");
        receiver1 = new Client("Bartosh", "Siekan", "123123123");
        clientRepository.add(shipper1);
        clientRepository.add(receiver1);
        lockerRepository.add(locker);
    }

    @AfterEach
    void eachFinisher() {
        deliveryRepository.findAll().forEach(deliveryRepository::remove);
    }

    @Test
    void Should_ThrowExceptionOnMakeDelivery_WhenGivenClientNotInDB() {
        assertThrows(
            NoResultException.class,
            () ->
                deliveryService.makeParcelDelivery(
                    basePrice,
                    10,
                    20,
                    30,
                    10,
                    false,
                    "0",
                    receiver1.getTelNumber(),
                    locker.getIdentityNumber()));
    }

    @Test
    void Should_ThrowExceptionOnPutIn_WhenClientIsInactive() {
        User user = new Client("Mauris", "Kakel", UUID.randomUUID().toString().substring(0, 9));
        clientRepository.add(user);

        Delivery delivery =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                user.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());

        changeClientActiveStatus(user, false);

        Delivery refreshedDelivery = deliveryRepository.get(delivery.getId());
        assertThrows(
            DeliveryManagerException.class,
            () ->
                deliveryService.putInLocker(
                    refreshedDelivery.getId(),
                    refreshedDelivery.getLocker().getIdentityNumber(),
                    "123"));
    }

    private void changeClientActiveStatus(User user, boolean active) {
        User refreshedUser = clientRepository.get(user.getId());
        refreshedUser.setActive(active);
        clientRepository.update(refreshedUser);
    }

  @Test
  void Should_ThrowException_WhenDeliveryPutInAgain() {
    Delivery delivery =
        deliveryService.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());

        deliveryService.putInLocker(
            delivery.getId(), delivery.getLocker().getIdentityNumber(), "54321");
        Delivery refreshedDelivery = deliveryRepository.get(delivery.getId());

        assertThrows(
            DeliveryManagerException.class,
            () ->
                deliveryService.putInLocker(
                    refreshedDelivery.getId(),
                    refreshedDelivery.getLocker().getIdentityNumber(),
                    "65433"));

        locker = lockerRepository.get(locker.getId());
        deliveryService.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "54321");
    }

    @Test
    void Should_SetAllocationTime_WhenDeliveryPutInAndTookOut() {
        Delivery delivery =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());

        assertNull(delivery.getAllocationStart());
        assertNull(delivery.getAllocationStop());

        deliveryService.putInLocker(
            delivery.getId(), delivery.getLocker().getIdentityNumber(), "54321");
        locker = lockerRepository.get(locker.getId());
        delivery = deliveryRepository.get(delivery.getId());
        assertNotNull(delivery.getAllocationStart());

        deliveryService.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "54321");
        locker = lockerRepository.get(locker.getId());
        delivery = deliveryRepository.get(delivery.getId());
        assertNotNull(delivery.getAllocationStop());
    }

    @Test
    void Should_BlockDepositBox_WhenDeliveryPutInLocker() {
        Delivery delivery =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());
        int empty = locker.countEmpty();
        deliveryService.putInLocker(
            delivery.getId(), delivery.getLocker().getIdentityNumber(), "12345");
        locker = lockerRepository.get(locker.getId());

        assertEquals(empty - 1, locker.countEmpty());

        deliveryService.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "12345");
        locker = lockerRepository.get(locker.getId());
    }

    @Test
    void Should_UnlockDepositBox_WhenDeliveryTookOut() {
        Delivery delivery =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());
        deliveryService.putInLocker(
            delivery.getId(), delivery.getLocker().getIdentityNumber(), "54321");
        locker = lockerRepository.get(locker.getId());
        int empty = locker.countEmpty();
        deliveryService.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "54321");
        locker = lockerRepository.get(locker.getId());

        assertEquals(empty + 1, locker.countEmpty());
    }

    @Test
    void Should_ThrowException_WhenLockerIsFull() {
        Locker oneBoxLocker = new Locker("LDZ12", "Gawronska 66, Lodz 12-123", 1);
        lockerRepository.add(oneBoxLocker);

        Delivery testDelivery =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                oneBoxLocker.getIdentityNumber());
        deliveryService.putInLocker(
            testDelivery.getId(), testDelivery.getLocker().getIdentityNumber(), "1111");
        locker = lockerRepository.get(oneBoxLocker.getId());

        assertThrows(
            DeliveryManagerException.class,
            () ->
                deliveryService.putInLocker(
                    testDelivery.getId(), testDelivery.getLocker().getIdentityNumber(), "1111"));

        deliveryService.takeOutDelivery(testDelivery.getId(), receiver1.getTelNumber(), "1111");
        locker = lockerRepository.get(oneBoxLocker.getId());
    }

    @Test
    void Should_ReturnAllClientDeliveries() {
        Delivery delivery =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());
        Delivery delivery1 =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());

        assertEquals(delivery, deliveryService.getAllClientDeliveries(receiver1).get(0));
        assertEquals(delivery1, deliveryService.getAllClientDeliveries(receiver1).get(1));
    }

    @Test
    void Should_ReturnAllReceivedDeliveriesForGivenClient() {
        Delivery delivery1 =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());
        deliveryService.putInLocker(delivery1.getId(), delivery1.getLocker().getIdentityNumber(), "2222");
        locker = lockerRepository.get(locker.getId());
        deliveryService.takeOutDelivery(delivery1.getId(), receiver1.getTelNumber(), "2222");
        locker = lockerRepository.get(locker.getId());

        assertTrue(0 < deliveryService.getAllReceivedClientDeliveries(receiver1.getTelNumber()).size());
    }

    @Test
    void Should_ReturnAllCurrentDeliveriesForGivenClient() {
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());

        assertTrue(0 < deliveryService.getAllCurrentClientDeliveries(receiver1.getTelNumber()).size());
    }

    @Test
    void Should_ReturnCorrectBalanceForClientShipments() {
        Delivery delivery =
            deliveryService.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());
        deliveryService.putInLocker(delivery.getId(), delivery.getLocker().getIdentityNumber(), "5555");
        locker = lockerRepository.get(locker.getId());

        assertEquals(new BigDecimal("15.000"), deliveryService.checkClientShipmentBalance(shipper1));

        deliveryService.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "5555");
        locker = lockerRepository.get(locker.getId());
    }

  @Test
  void Should_ThrowException_WhenInvalidValuesPassed() {
    assertThrows(
        DeliveryManagerException.class, () -> deliveryService.checkClientShipmentBalance(null));
  }

}
