package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.locker.DepositBox;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.model.locker.DepositBoxEnt;
import pl.pas.infrastructure.model.locker.LockerEnt;

public class LockerMapper {

    public static Locker mapToDomain(LockerEnt lockerEnt) {
        return lockerEnt == null ? null : new Locker(
            lockerEnt.getId(),
            lockerEnt.getIdentityNumber(),
            lockerEnt.getAddress(),
            lockerEnt.getDepositBoxes().stream().map(LockerMapper::mapToDomain).toList()
        );
    }

    public static LockerEnt mapToEntity(Locker locker) {
        return locker == null ? null : new LockerEnt(
            locker.getId(),
            locker.getIdentityNumber(),
            locker.getAddress(),
            locker.getDepositBoxes().stream().map(LockerMapper::mapToEntity).toList()
        );
    }

    private static DepositBox mapToDomain(DepositBoxEnt depositBoxEnt) {
        return depositBoxEnt == null ? null : new DepositBox(
            depositBoxEnt.getId(),
            DeliveryMapper.mapToDomain(depositBoxEnt.getDelivery()),
            depositBoxEnt.isEmpty(),
            depositBoxEnt.getAccessCode(),
            depositBoxEnt.getTelNumber()
        );
    }

    private static DepositBoxEnt mapToEntity(DepositBox depositBox) {
        return depositBox == null ? null : new DepositBoxEnt(
            depositBox.getId(),
            DeliveryMapper.mapToEntity(depositBox.getDelivery()),
            depositBox.isEmpty(),
            depositBox.getAccessCode(),
            depositBox.getTelNumber()
        );
    }

}
