package pl.pas.infrastructure.model.delivery;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.configuration.ListConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "LIST")
public class ListEnt extends PackageEnt {

    private boolean priority;

    public ListEnt(BigDecimal basePrice, boolean priority) {
        super(basePrice);

        this.priority = priority;
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = basePrice.divide(ListConfig.RATIO, RoundingMode.FLOOR);
        if (priority) cost = cost.add(ListConfig.ADDITIONAL_COST);
        return cost;
    }
}
