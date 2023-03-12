package pl.pas.core.applicationmodel.model.locker;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.EntityModel;

import java.util.UUID;


@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class DepositBox {

    private UUID id;
    private Delivery delivery;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBox() {
        isEmpty = true;
        telNumber = "";
        accessCode = "";
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
