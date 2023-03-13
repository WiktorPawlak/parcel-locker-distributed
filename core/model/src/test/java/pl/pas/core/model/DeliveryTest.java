package pl.pas.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pas.core.applicationmodel.model.delivery.DeliveryStatus.READY_TO_PICKUP;
import static pl.pas.core.applicationmodel.model.delivery.DeliveryStatus.READY_TO_SHIP;
import static pl.pas.core.applicationmodel.model.delivery.DeliveryStatus.RECEIVED;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryTest {

    private final BigDecimal basePrice = BigDecimal.TEN;
    private Delivery delivery1;
    private Delivery delivery2;
    private Client shipper1;
    private Client receiver1;
    private Locker locker;

    @BeforeAll
    void setup() {
        locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 20);
        shipper1 = new Client("Oscar", "Trel", "321312312");
        receiver1 = new Client("Bartosh", "Siekan", "123123123");
        delivery1 = new Delivery(basePrice, 10, 20, 30, 10, true, shipper1, receiver1, locker.getId());
        delivery2 = new Delivery(basePrice, true, shipper1, receiver1, locker.getId());
    }

    @Test
    void Should_ReturnCorrectObjectsForParcelDelivery_WhenGettersCalled() {
        assertEquals(shipper1, delivery1.getShipper());
        assertEquals(receiver1, delivery1.getReceiver());
        assertEquals(READY_TO_SHIP, delivery1.getStatus());
        assertEquals(locker.getId(), delivery1.getLockerId());
    }

    @Test
    void Should_ReturnCorrectObjectsForListDelivery_WhenGettersCalled() {
        assertEquals(shipper1, delivery2.getShipper());
        assertEquals(receiver1, delivery2.getReceiver());
        assertEquals(READY_TO_SHIP, delivery2.getStatus());
        assertEquals(locker.getId(), delivery2.getLockerId());
    }

    @Test
    void Should_SetterSetCorrectStatus() {
        assertEquals(READY_TO_SHIP, delivery1.getStatus());
        delivery1.setStatus(READY_TO_PICKUP);
        assertEquals(READY_TO_PICKUP, delivery1.getStatus());
        delivery1.setStatus(RECEIVED);
        assertEquals(RECEIVED, delivery1.getStatus());
    }
}
