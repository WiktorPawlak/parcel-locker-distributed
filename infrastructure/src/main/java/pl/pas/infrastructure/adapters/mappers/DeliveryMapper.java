package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.delivery.Package;
import pl.pas.infrastructure.model.delivery.DeliveryEntity;
import pl.pas.infrastructure.model.delivery.PackageEntity;

public class DeliveryMapper {

    public static DeliveryEntity mapToEntity(Delivery delivery) {
        if (delivery == null) return null;

        PackageEntity pack = PackageMapper.mapToEntity(delivery.getPack());

        return new DeliveryEntity(
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

    public static Delivery mapToDomain(DeliveryEntity delivery) {
        if (delivery == null) return null;

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
