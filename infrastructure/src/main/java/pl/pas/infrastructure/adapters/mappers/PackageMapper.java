package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.delivery.List;
import pl.pas.core.applicationmodel.model.delivery.Package;
import pl.pas.core.applicationmodel.model.delivery.Parcel;
import pl.pas.infrastructure.model.delivery.ListEntity;
import pl.pas.infrastructure.model.delivery.PackageEntity;
import pl.pas.infrastructure.model.delivery.ParcelEntity;

public class PackageMapper {

    private static Parcel mapToDomain(ParcelEntity parcelEnt) {
        return parcelEnt == null ? null : new Parcel(
            parcelEnt.basePrice,
            parcelEnt.getWidth(),
            parcelEnt.getLength(),
            parcelEnt.getHeight(),
            parcelEnt.getWeight(),
            parcelEnt.isFragile()
        );
    }

    private static ParcelEntity mapToEntity(Parcel parcel) {
        return parcel == null ? null : new ParcelEntity(
            parcel.basePrice,
            parcel.getWidth(),
            parcel.getLength(),
            parcel.getHeight(),
            parcel.getWeight(),
            parcel.isFragile()
        );
    }

    private static List mapToDomain(ListEntity listEntity) {
        return listEntity == null ? null : new List(listEntity.basePrice, listEntity.isPriority());
    }

    private static ListEntity mapToEntity(List list) {
        return list == null ? null : new ListEntity(list.basePrice, list.isPriority());
    }

    public static Package mapToDomain(PackageEntity packageEntity) {
        return packageEntity instanceof ParcelEntity ?
            PackageMapper.mapToDomain((ParcelEntity) packageEntity) :
            mapToDomain((ListEntity) packageEntity);
    }

    public static PackageEntity mapToEntity(Package pack) {
        return pack instanceof Parcel ?
            PackageMapper.mapToEntity((Parcel) pack) :
            mapToEntity((List) pack);
    }

}
