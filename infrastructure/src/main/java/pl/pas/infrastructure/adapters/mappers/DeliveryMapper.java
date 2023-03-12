package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.delivery.Package;
import pl.pas.infrastructure.model.delivery.DeliveryEnt;
import pl.pas.infrastructure.model.delivery.PackageEnt;

public class DeliveryMapper {

    public static DeliveryEnt mapToEntity(Delivery delivery) {
        if(delivery == null) return null;

        PackageEnt pack = PackageMapper.mapToEntity(delivery.getPack());

        return new DeliveryEnt(
            delivery.getId(),
            ClientMapper.mapToEntity(delivery.getShipper()),
            ClientMapper.mapToEntity(delivery.getReceiver()),
            DeliveryStatusMapper.mapToEntity(delivery.getStatus()),
            pack,
            LockerMapper.mapToEntity(delivery.getLocker()),
            delivery.getAllocationStart(),
            delivery.getAllocationStop(),
            delivery.isArchived()
        );
    }

    public static Delivery mapToDomain(DeliveryEnt delivery) {
        if(delivery == null) return null;

        Package pack = PackageMapper.mapToDomain(delivery.getPack());

        return new Delivery(
            delivery.getId(),
            ClientMapper.mapToDomain(delivery.getShipper()),
            ClientMapper.mapToDomain(delivery.getReceiver()),
            DeliveryStatusMapper.mapToDomain(delivery.getStatus()),
            pack,
            LockerMapper.mapToDomain(delivery.getLocker()),
            delivery.getAllocationStart(),
            delivery.getAllocationStop(),
            delivery.isArchived()
        );
    }

}
