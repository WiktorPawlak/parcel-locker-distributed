package pl.pas.domain.infrastructure.model.locker;

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
import pl.pas.domain.infrastructure.model.EntityModel;

@Slf4j
@Entity
@Table(name = "LOCKERS")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class LockerEntity extends EntityModel {

    private String identityNumber;
    private String address;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<DepositBoxEntity> depositBoxes;

    public LockerEntity(UUID id, Long version, String identityNumber, String address, List<DepositBoxEntity> depositBoxes) {
        super(id, version);
        this.identityNumber = identityNumber;
        this.address = address;
        this.depositBoxes = depositBoxes;
    }

}
