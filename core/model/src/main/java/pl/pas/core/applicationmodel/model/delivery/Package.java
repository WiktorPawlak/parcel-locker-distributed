package pl.pas.core.applicationmodel.model.delivery;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class Package {
    private UUID id; //TODO dodać do ctorów i mapperów
    public BigDecimal basePrice;

    public Package(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
