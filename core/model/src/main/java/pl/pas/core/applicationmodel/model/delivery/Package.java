package pl.pas.core.applicationmodel.model.delivery;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.core.applicationmodel.model.EntityModel;

@Entity
@NoArgsConstructor
public abstract class Package extends EntityModel {
    public BigDecimal basePrice;

    public Package(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
