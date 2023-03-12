package pl.pas.core.applicationmodel.model.delivery;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public abstract class Package {
    private UUID id;
    public BigDecimal basePrice;

    public Package(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
