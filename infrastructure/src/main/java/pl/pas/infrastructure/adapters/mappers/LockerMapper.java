package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.locker.DepositBox;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.model.locker.DepositBoxEnt;
import pl.pas.infrastructure.model.locker.LockerEnt;

import java.util.List;
import java.util.stream.Collectors;

public class LockerMapper {

    public static Locker mapToDomain(LockerEnt lockerEnt) {
        return lockerEnt == null ? null : new Locker(
            lockerEnt.getId(),
            lockerEnt.getIdentityNumber(),
            lockerEnt.getAddress(),
            DepositBoxMapper.mapToDomain(lockerEnt.getDepositBoxes())
        );
    }

    public static LockerEnt mapToEntity(Locker locker) {
        return locker == null ? null : new LockerEnt(
            locker.getId(),
            locker.getIdentityNumber(),
            locker.getAddress(),
            DepositBoxMapper.mapToEntity(locker.getDepositBoxes())
        );
    }

}
