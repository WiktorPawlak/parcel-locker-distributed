package pl.pas.domain.core.applicationmodel.model.delivery;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class Package {

    private final UUID id;

    private final Long version;

    private BigDecimal basePrice;

    protected Package(UUID id, Long version, BigDecimal basePrice) {
        this(id, version);
        this.basePrice = basePrice;
    }

    private Package(UUID id, Long version) {
        this.id = id;
        this.version = version;
    }

    public abstract BigDecimal getCost();
}
