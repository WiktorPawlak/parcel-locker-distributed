package pl.pas.domain.core.applicationmodel.model.delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import lombok.Getter;
import pl.pas.domain.core.applicationmodel.configuration.ListConfig;

@Getter
public class List extends Package {

    private final boolean priority;

    public List(BigDecimal basePrice, boolean priority) {
        this(UUID.randomUUID(), 0L, basePrice, priority);
    }

    public List(UUID id, Long version, BigDecimal basePrice, boolean priority) {
        super(id, version, basePrice);

        this.priority = priority;
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = getBasePrice().divide(ListConfig.RATIO, RoundingMode.FLOOR);
        if (priority) cost = cost.add(ListConfig.ADDITIONAL_COST);
        return cost;
    }
}
