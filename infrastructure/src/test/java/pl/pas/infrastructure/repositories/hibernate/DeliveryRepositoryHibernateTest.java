package pl.pas.infrastructure.repositories.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.persistence.NoResultException;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.adapters.mappers.ClientMapper;
import pl.pas.infrastructure.adapters.mappers.LockerMapper;
import pl.pas.infrastructure.model.delivery.DeliveryEntity;
import pl.pas.infrastructure.model.delivery.DeliveryStatusEntity;
import pl.pas.infrastructure.model.locker.LockerEntity;
import pl.pas.infrastructure.model.user.ClientEntity;
import pl.pas.infrastructure.repositories.config.TestsConfig;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryRepositoryHibernateTest extends TestsConfig {

    private ClientEntity c1;
    private ClientEntity c2;
    private LockerEntity l1;

    @BeforeAll
    void setup() {
        l1 = LockerMapper.mapToEntity(new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 10));
        c1 = ClientMapper.mapToEntity(new Client("Maciej", "Nowak", "12345"));
        c2 = ClientMapper.mapToEntity(new Client("Maciej", "Kowal", "123456"));
        clientRepository.add(c1);
        clientRepository.add(c2);
        lockerRepository.add(l1);
    }

    @Test
    void Should_CreateDelivery() {
        DeliveryEntity delivery = new DeliveryEntity(BigDecimal.TEN, true, c1, c2, l1, 0L);
        deliveryRepository.add(delivery);
        delivery = deliveryRepository.get(delivery.getId());
        assertEquals(deliveryRepository.get(delivery.getId()), delivery);
    }

    @Test
    void Should_UpdateDelivery() {
        DeliveryEntity delivery = new DeliveryEntity(BigDecimal.TEN, true, c1, c2, l1, 0L);
        deliveryRepository.add(delivery);
        assertEquals(DeliveryStatusEntity.READY_TO_SHIP, deliveryRepository.get(delivery.getId()).getStatus());
        delivery.setStatus(DeliveryStatusEntity.READY_TO_PICKUP);
        deliveryRepository.update(delivery);
        assertEquals(DeliveryStatusEntity.READY_TO_PICKUP, deliveryRepository.get(delivery.getId()).getStatus());
    }

    @Test
    void Should_DeleteDelivery() {
        DeliveryEntity delivery = new DeliveryEntity(BigDecimal.TEN, true, c1, c2, l1, 0L);
        deliveryRepository.add(delivery);
        assertEquals(deliveryRepository.get(delivery.getId()), delivery);
        deliveryRepository.remove(delivery);
        assertThrows(NoResultException.class, () -> deliveryRepository.get(delivery.getId()));
    }

    @Test
    void Should_ArchiveDelivery() {
        DeliveryEntity delivery = new DeliveryEntity(BigDecimal.TEN, true, c1, c2, l1, 0L);
        deliveryRepository.add(delivery);
        assertFalse(deliveryRepository.get(delivery.getId()).isArchived());
        deliveryRepository.archive(delivery.getId());
        assertTrue(deliveryRepository.get(delivery.getId()).isArchived());
    }

    @Test
    void Should_ReturnAllDeliveries() {
        DeliveryEntity delivery = new DeliveryEntity(BigDecimal.TEN, true, c1, c2, l1, 0L);
        DeliveryEntity delivery1 = new DeliveryEntity(BigDecimal.ONE, false, c2, c1, l1, 0L);
        deliveryRepository.add(delivery);
        deliveryRepository.add(delivery1);
        assertTrue(deliveryRepository.findAll().contains(delivery));
        assertTrue(deliveryRepository.findAll().contains(delivery1));
    }
}
