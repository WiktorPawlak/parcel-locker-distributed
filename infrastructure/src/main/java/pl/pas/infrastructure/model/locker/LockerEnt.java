package pl.pas.infrastructure.model.locker;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.pas.infrastructure.model.EntityModel;

@Slf4j
@Entity
@Table(name = "Lockers")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class LockerEnt extends EntityModel {

    private String identityNumber;
    private String address;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<DepositBoxEnt> depositBoxes;

    public LockerEnt(UUID id, String identityNumber, String address, List<DepositBoxEnt> depositBoxes) {
        super(id);
        this.identityNumber = identityNumber;
        this.address = address;
        this.depositBoxes = depositBoxes;
    }

}
