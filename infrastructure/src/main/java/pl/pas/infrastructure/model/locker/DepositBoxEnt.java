package pl.pas.infrastructure.model.locker;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import pl.pas.infrastructure.model.EntityModel;
import pl.pas.infrastructure.model.delivery.DeliveryEnt;

import java.util.UUID;

@Entity
@Table(name = "DepositBoxes")
@EqualsAndHashCode
public class DepositBoxEnt extends EntityModel {

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryEnt delivery;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBoxEnt() {
        isEmpty = true;
        telNumber = "";
        accessCode = "";
    }

    public DepositBoxEnt(UUID id, DeliveryEnt delivery, boolean isEmpty, String accessCode, String telNumber) {
        super(id);
        this.delivery = delivery;
        this.isEmpty = isEmpty;
        this.accessCode = accessCode;
        this.telNumber = telNumber;
    }

    public boolean canAccess(String code, String telNumber) {
        return code.equals(this.accessCode)
            && telNumber.equals(this.telNumber)
            && !code.isEmpty()
            && !telNumber.isEmpty();
    }

    public void putIn(DeliveryEnt delivery, String telNumber, String accessCode) {
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

    public DeliveryEnt getDelivery() {
        return delivery;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
