package pl.pas.infrastructure.repositories.hibernate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.adapters.mappers.LockerMapper;
import pl.pas.infrastructure.model.locker.LockerEntity;
import pl.pas.infrastructure.repositories.config.TestsConfig;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LockerRepositoryHibernateTest extends TestsConfig {
    private LockerEntity l1;

    @BeforeAll
    void setup() {
        l1 = LockerMapper.mapToEntity(new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 10));
        lockerRepository.add(l1);
    }

    @Test
    void Should_CreateLocker() {
        assertDoesNotThrow(() -> lockerRepository.get(l1.getId()));
    }

    @Test
    void Should_RemoveLocker() {
        int count = lockerRepository.size();
        lockerRepository.remove(l1);
        assertTrue(count > lockerRepository.size());
    }
}
