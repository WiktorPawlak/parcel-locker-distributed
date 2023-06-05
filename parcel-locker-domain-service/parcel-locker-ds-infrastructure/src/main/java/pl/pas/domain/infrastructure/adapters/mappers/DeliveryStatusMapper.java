package pl.pas.domain.infrastructure.adapters.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.domain.core.applicationmodel.model.delivery.DeliveryStatus;
import pl.pas.domain.infrastructure.model.delivery.DeliveryStatusEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryStatusMapper {

    public static DeliveryStatus mapToDomain(DeliveryStatusEntity deliveryStatusEntity) {
        return DeliveryStatus.valueOf(deliveryStatusEntity.name());
    }

    public static DeliveryStatusEntity mapToEntity(DeliveryStatus deliveryStatus) {
        return DeliveryStatusEntity.valueOf(deliveryStatus.name());
    }

}
