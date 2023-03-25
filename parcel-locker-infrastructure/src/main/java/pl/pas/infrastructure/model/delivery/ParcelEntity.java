package pl.pas.infrastructure.model.delivery;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.core.applicationmodel.configuration.ParcelConfig;
import pl.pas.core.applicationmodel.exceptions.ParcelException;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("PARCEL")
public class ParcelEntity extends PackageEntity {
    private double width;
    private double length;
    private double height;
    private double weight;
    private boolean fragile;

    public ParcelEntity(UUID id, Long version,
                        BigDecimal basePrice,
                        double width, double length, double height, double weight,
                        boolean fragile) {
        super(id, version, basePrice);

        validateSize(width);
        validateSize(length);
        validateSize(height);
        validateWeight(weight);

        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.fragile = fragile;
    }

    private void validateSize(double size) {
        if (size <= ParcelConfig.MIN_PARCEL_SIZE || size > ParcelConfig.MAX_PARCEL_SIZE)
            throw new ParcelException("invalid size value!");
    }

    private void validateWeight(double weight) {
        if (weight <= ParcelConfig.MIN_PARCEL_WEIGHT || weight > ParcelConfig.MAX_PARCEL_WEIGHT)
            throw new ParcelException("invalid weight value!");
    }
}
