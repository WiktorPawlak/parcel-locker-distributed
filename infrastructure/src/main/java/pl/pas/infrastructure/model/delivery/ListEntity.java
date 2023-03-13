package pl.pas.infrastructure.model.delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.configuration.ListConfig;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "LIST")
public class ListEntity extends PackageEntity {

    private boolean priority;

    public ListEntity(BigDecimal basePrice, boolean priority) {
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
