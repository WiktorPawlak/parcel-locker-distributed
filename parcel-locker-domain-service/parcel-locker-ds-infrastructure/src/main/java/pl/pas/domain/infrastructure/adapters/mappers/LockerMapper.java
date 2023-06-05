package pl.pas.domain.infrastructure.adapters.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.domain.core.applicationmodel.model.locker.Locker;
import pl.pas.domain.infrastructure.model.locker.LockerEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LockerMapper {

    public static Locker mapToDomain(LockerEntity lockerEntity) {
        return lockerEntity == null ? null : new Locker(
            lockerEntity.getId(),
            lockerEntity.getVersion(),
            lockerEntity.getIdentityNumber(),
            lockerEntity.getAddress(),
            DepositBoxMapper.mapToDomain(lockerEntity.getDepositBoxes())
        );
    }

    public static LockerEntity mapToEntity(Locker locker) {
        return locker == null ? null : new LockerEntity(
            locker.getId(),
            locker.getVersion(),
            locker.getIdentityNumber(),
            locker.getAddress(),
            DepositBoxMapper.mapToEntity(locker.getDepositBoxes())
        );
    }

}
