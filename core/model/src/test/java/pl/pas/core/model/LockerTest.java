package pl.pas.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.locker.DepositBox;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;

class LockerTest {

    private Client c1 = new Client("test", "test", "1231");
    private Client c2 = new Client("test", "test", "1231");
    private Locker locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 10);
    private Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, locker.getId());


    @Test
    void Should_ReturnNumberOfEmptyDepositBoxes() {
        Locker newLocker = new Locker("LDZ02", "Gawronska 26, Lodz 12-123", 10);
        assertEquals(10, newLocker.countEmpty());
    }

    @Test
    void Should_UpdateDepositBoxFieldsAndSetIsEmptyAsFalse_WhenPutIn() {
        UUID depositBoxId = locker.putIn(delivery, "123456789", "k0z4k");

        DepositBox depositBox = locker.getDepositBox(depositBoxId);
        assertEquals("123456789", depositBox.getTelNumber());
        assertEquals("k0z4k", depositBox.getAccessCode());
        assertEquals(delivery.getId(), depositBox.getDelivery().getId());
    }

}
