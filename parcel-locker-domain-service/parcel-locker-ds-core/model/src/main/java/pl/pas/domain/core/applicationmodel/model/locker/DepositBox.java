package pl.pas.domain.core.applicationmodel.model.locker;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.pas.domain.core.applicationmodel.model.delivery.Delivery;


@EqualsAndHashCode
@Getter
@Setter
public class DepositBox {

    private UUID id;
    private Long version;
    private Delivery delivery;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBox(final UUID id, final Long version,
                      final Delivery delivery,
                      final boolean isEmpty,
                      final String accessCode, final String telNumber) {
        this(id, version);
        this.delivery = delivery;
        this.isEmpty = isEmpty;
        this.accessCode = accessCode;
        this.telNumber = telNumber;
    }

    public DepositBox() {
        this(UUID.randomUUID(), 0L);
        isEmpty = true;
        telNumber = "";
        accessCode = "";
    }

    private DepositBox(UUID id, Long version) {
        this.id = id;
        this.version = version;
    }

    public boolean canAccess(String code, String telNumber) {
        return code.equals(this.accessCode)
            && telNumber.equals(this.telNumber)
            && !code.isEmpty()
            && !telNumber.isEmpty();
    }

    public void putIn(Delivery delivery, String telNumber, String accessCode) {
        this.accessCode = accessCode;
        this.isEmpty = false;
        this.telNumber = telNumber;
        this.delivery = delivery;
    }

    public void clean() {
        isEmpty = true;
        this.accessCode = "";
        this.telNumber = "";
        this.delivery = null;
    }

}
