package pl.pas.infrastructure.model.locker;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.pas.infrastructure.model.EntityModel;
import pl.pas.infrastructure.model.delivery.DeliveryEntity;

@Entity
@Table(name = "DEPOSIT_BOXES")
@Getter
@EqualsAndHashCode(callSuper = false)
public class DepositBoxEntity extends EntityModel {

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity delivery;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBoxEntity() {
        isEmpty = true;
        telNumber = "";
        accessCode = "";
    }

    public DepositBoxEntity(UUID id, Long version, DeliveryEntity delivery, boolean isEmpty, String accessCode, String telNumber) {
        super(id, version);
        this.delivery = delivery;
        this.isEmpty = isEmpty;
        this.accessCode = accessCode;
        this.telNumber = telNumber;
    }
}
