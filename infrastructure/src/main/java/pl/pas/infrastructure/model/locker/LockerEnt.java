package pl.pas.infrastructure.model.locker;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.pas.core.applicationmodel.exceptions.LockerException;
import pl.pas.infrastructure.model.EntityModel;
import pl.pas.infrastructure.model.delivery.DeliveryEnt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class LockerEnt extends EntityModel {

    private String identityNumber;
    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<DepositBoxEnt> depositBoxes;

    public LockerEnt(UUID id, String identityNumber, String address, List<DepositBoxEnt> depositBoxes) {
        super(id);
        this.identityNumber = identityNumber;
        this.address = address;
        this.depositBoxes = depositBoxes;
    }
}
