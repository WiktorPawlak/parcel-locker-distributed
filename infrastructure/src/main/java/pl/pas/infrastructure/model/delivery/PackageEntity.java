package pl.pas.infrastructure.model.delivery;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import pl.pas.infrastructure.model.EntityModel;

@Entity
@Table(name = "PACKAGES")
@NoArgsConstructor
public abstract class PackageEntity extends EntityModel {
    public BigDecimal basePrice;

    public PackageEntity(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
