package pl.pas.ports.adapters.mappers;

import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.infrastructure.model.delivery.DeliveryEnt;

public class DeliveryMapper {

    public static DeliveryEnt mapToEntity(Delivery delivery) {
        return new DeliveryEnt();
    }

    public static Delivery mapToDomain(DeliveryEnt delivery) {
        return new Delivery();
    }

}
