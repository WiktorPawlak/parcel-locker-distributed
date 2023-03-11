package pl.pas.core.applicationmodel.model.delivery;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.configuration.ListConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@NoArgsConstructor
@DiscriminatorColumn(name = "LIST")
public class List extends Package {

    private boolean priority;

    public List(BigDecimal basePrice, boolean priority) {
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
