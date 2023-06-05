package pl.pas.domain.core.applicationmodel.model.locker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.pas.domain.core.applicationmodel.exceptions.LockerException;
import pl.pas.domain.core.applicationmodel.model.delivery.Delivery;

@Slf4j
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Locker {

    private UUID id;
    private Long version;
    private String identityNumber;
    private String address;
    private List<DepositBox> depositBoxes;

    public Locker(String identityNumber, String address, int boxAmount) {
        this(UUID.randomUUID(), 0L, identityNumber, address, boxAmount);
    }

    public Locker(UUID id, Long version, String identityNumber, String address, List<DepositBox> depositBoxes) {
        this(id, version, identityNumber, address);
        this.depositBoxes = depositBoxes;
    }

    public Locker(UUID id, Long version, String identityNumber, String address, int boxAmount) {
        this(id, version, identityNumber, address);
        try {
            if (boxAmount <= 0)
                throw new LockerException("Locker with 0 boxes can not be created!");
        } catch (LockerException e) {
            log.error(e.getMessage());
        }

        depositBoxes = new ArrayList<>();
        for (int i = 0; i < boxAmount; i++) {
            depositBoxes.add(new DepositBox());
        }
    }

    private Locker(UUID id, Long version, String identityNumber, String address) {
        this.id = id;
        this.version = version;
        this.identityNumber = identityNumber;
        this.address = address;
    }

    public UUID putIn(Delivery delivery, String telNumber, String accessCode) {

        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.isEmpty()) {
                depositBox.putIn(delivery, telNumber, accessCode);
                return depositBox.getId();
            }
        }
        throw new LockerException("Not able to put package with id = " +
            delivery.getId() + " into locker " + this.getIdentityNumber() + ".");
    }

    public Delivery takeOut(String telNumber, String code) {
        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.canAccess(code, telNumber)) {
                Delivery takenOutDelivery = depositBox.getDelivery();
                depositBox.clean();
                return takenOutDelivery;
            }
        }
        throw new LockerException("Couldn't get any package out with access code: "
            + code
            + "and phone number: "
            + telNumber);
    }

    public int countEmpty() {
        int counter = 0;
        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.isEmpty()) {
                counter++;
            }
        }
        return counter;
    }

    public DepositBox getDepositBox(UUID id) {
        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.getId().equals(id)) {
                return depositBox;
            }
        }
        return null;
    }
}
