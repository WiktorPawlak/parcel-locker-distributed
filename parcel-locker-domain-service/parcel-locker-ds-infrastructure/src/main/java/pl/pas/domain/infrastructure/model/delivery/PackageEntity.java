package pl.pas.domain.infrastructure.model.delivery;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.pas.domain.infrastructure.model.EntityModel;

@Entity
@Table(name = "PACKAGES")
@NoArgsConstructor
public abstract class PackageEntity extends EntityModel {

    @Getter
    private BigDecimal basePrice;

    protected PackageEntity(UUID id, Long version, BigDecimal basePrice) {
        super(id, version);
        this.basePrice = basePrice;
    }
}
