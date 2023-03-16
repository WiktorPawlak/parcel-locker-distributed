package pl.pas.infrastructure.model.delivery;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("LIST")
public class ListEntity extends PackageEntity {

    private boolean priority;

    public ListEntity(UUID id, Long version, BigDecimal basePrice, boolean priority) {
        super(id, version, basePrice);

        this.priority = priority;
    }
}
