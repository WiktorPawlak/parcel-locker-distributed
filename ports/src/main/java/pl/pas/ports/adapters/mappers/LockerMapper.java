package pl.pas.ports.adapters.mappers;

import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.model.locker.LockerEnt;

public class LockerMapper {

    public static LockerEnt mapToEntity(Locker locker) {
        return new LockerEnt();
    }

    public static Locker mapToDomain(LockerEnt locker) {
        return new Locker();
    }

}
