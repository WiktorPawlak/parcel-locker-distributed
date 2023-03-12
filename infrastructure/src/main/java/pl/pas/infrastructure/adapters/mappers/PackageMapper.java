package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.delivery.List;
import pl.pas.core.applicationmodel.model.delivery.Package;
import pl.pas.core.applicationmodel.model.delivery.Parcel;
import pl.pas.infrastructure.model.delivery.ListEnt;
import pl.pas.infrastructure.model.delivery.PackageEnt;
import pl.pas.infrastructure.model.delivery.ParcelEnt;

public class PackageMapper {

    private static Parcel mapToDomain(ParcelEnt parcelEnt) {
        return parcelEnt == null ? null : new Parcel(
            parcelEnt.basePrice,
            parcelEnt.getWidth(),
            parcelEnt.getLength(),
            parcelEnt.getHeight(),
            parcelEnt.getWeight(),
            parcelEnt.isFragile()
        );
    }

    private static ParcelEnt mapToEntity(Parcel parcel) {
        return parcel == null ? null : new ParcelEnt(
            parcel.basePrice,
            parcel.getWidth(),
            parcel.getLength(),
            parcel.getHeight(),
            parcel.getWeight(),
            parcel.isFragile()
        );
    }

    private static List mapToDomain(ListEnt listEnt) {
        return listEnt == null ? null : new List(listEnt.basePrice, listEnt.isPriority());
    }

    private static ListEnt mapToEntity(List list) {
        return list == null ? null : new ListEnt(list.basePrice, list.isPriority());
    }

    public static Package mapToDomain(PackageEnt packageEnt) {
        return packageEnt instanceof ParcelEnt ?
            PackageMapper.mapToDomain((ParcelEnt) packageEnt) :
            mapToDomain((ListEnt) packageEnt);
    }

    public static PackageEnt mapToEntity(Package pack) {
        return pack instanceof Parcel ?
            PackageMapper.mapToEntity((Parcel) pack) :
            mapToEntity((List) pack);
    }

}
