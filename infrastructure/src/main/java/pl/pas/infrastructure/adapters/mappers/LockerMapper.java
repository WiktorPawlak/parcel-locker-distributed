package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.model.locker.LockerEntity;

public class LockerMapper {

    public static Locker mapToDomain(LockerEntity lockerEntity) {
        return lockerEntity == null ? null : new Locker(
            lockerEntity.getId(),
            lockerEntity.getIdentityNumber(),
            lockerEntity.getAddress(),
            DepositBoxMapper.mapToDomain(lockerEntity.getDepositBoxes())
        );
    }

    public static LockerEntity mapToEntity(Locker locker) {
        return locker == null ? null : new LockerEntity(
            locker.getId(),
            locker.getIdentityNumber(),
            locker.getAddress(),
            DepositBoxMapper.mapToEntity(locker.getDepositBoxes())
        );
    }

}
