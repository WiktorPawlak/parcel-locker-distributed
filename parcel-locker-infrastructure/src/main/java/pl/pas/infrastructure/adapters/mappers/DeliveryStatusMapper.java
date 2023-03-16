package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.delivery.DeliveryStatus;
import pl.pas.infrastructure.model.delivery.DeliveryStatusEntity;

public class DeliveryStatusMapper {

    public static DeliveryStatus mapToDomain(DeliveryStatusEntity deliveryStatusEntity) {
        return DeliveryStatus.valueOf(deliveryStatusEntity.name());
    }

    public static DeliveryStatusEntity mapToEntity(DeliveryStatus deliveryStatus) {
        return DeliveryStatusEntity.valueOf(deliveryStatus.name());
    }

}
