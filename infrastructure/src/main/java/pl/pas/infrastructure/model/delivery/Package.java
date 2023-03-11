package pl.pas.infrastructure.model.delivery;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.pas.infrastructure.model.EntityModel;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public abstract class Package extends EntityModel {
    public BigDecimal basePrice;

    public Package(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
