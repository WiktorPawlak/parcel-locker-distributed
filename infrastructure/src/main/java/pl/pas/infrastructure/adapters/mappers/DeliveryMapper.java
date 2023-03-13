package pl.pas.infrastructure.adapters.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.delivery.Package;
import pl.pas.infrastructure.model.delivery.DeliveryEntity;
import pl.pas.infrastructure.model.delivery.PackageEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryMapper {

    public static DeliveryEntity mapToEntity(Delivery delivery) {
        if (delivery == null) return null;

        PackageEntity pack = PackageMapper.mapToEntity(delivery.getPack());

        return new DeliveryEntity(
            delivery.getId(),
            delivery.getVersion(),
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
            delivery.getVersion(),
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
