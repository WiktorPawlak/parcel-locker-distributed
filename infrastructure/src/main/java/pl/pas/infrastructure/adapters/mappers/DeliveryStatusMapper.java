package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.delivery.DeliveryStatus;
import pl.pas.infrastructure.model.delivery.DeliveryStatusEnt;

public class DeliveryStatusMapper {

    public static DeliveryStatus mapToDomain(DeliveryStatusEnt deliveryStatusEnt) {
        return DeliveryStatus.valueOf(deliveryStatusEnt.name());
    }

    public static DeliveryStatusEnt mapToEntity(DeliveryStatus deliveryStatus) {
        return DeliveryStatusEnt.valueOf(deliveryStatus.name());
    }

}
