package pl.pas.infrastructure.model.delivery;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import pl.pas.infrastructure.model.EntityModel;

import java.math.BigDecimal;

@Entity
@Table(name = "Packages")
@NoArgsConstructor
public abstract class PackageEnt extends EntityModel {
    public BigDecimal basePrice;

    public PackageEnt(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
