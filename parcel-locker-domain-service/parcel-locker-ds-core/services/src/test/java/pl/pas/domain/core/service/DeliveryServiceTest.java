package pl.pas.domain.core.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import lombok.SneakyThrows;
import pl.pas.domain.core.applicationmodel.model.locker.Locker;
import pl.pas.domain.core.applicationmodel.model.user.Client;
import pl.pas.domain.ports.incoming.DeliveryService;
import pl.pas.domain.ports.outcoming.DeliveryRepository;
import pl.pas.domain.ports.outcoming.LockerRepository;
import pl.pas.domain.ports.outcoming.UserRepository;


class DeliveryServiceTest {

    private AutoCloseable autoCloseable;
    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private LockerRepository lockerRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DeliveryService deliveryService = new DeliveryServiceImpl();

    private final BigDecimal basePrice = BigDecimal.TEN;
    private final Client shipper1 = new Client("Oscar", "Trel", "321312312");
    private final Client receiver1 = new Client("Bartosh", "Siekan", "123123123");
    private Locker locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 20);

    @BeforeEach
    public void beforeAll() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        Mockito.when(userRepository.findByTelNumber(shipper1.getTelNumber())).thenReturn(Optional.of(shipper1));
        Mockito.when(userRepository.findByTelNumber(receiver1.getTelNumber())).thenReturn(Optional.of(receiver1));
        Mockito.when(lockerRepository.findByIdentityNumber(locker.getIdentityNumber())).thenReturn(Optional.of(locker));
    }

    @After
    @SneakyThrows
    public void afterEach() {
        autoCloseable.close();
    }

    @Test
    void Should_ThrowExceptionOnMakeDelivery_WhenGivenClientNotInDB() {
        assertThrows(
            NoSuchElementException.class,
            () ->
                deliveryService.makeParcelDelivery(
                    basePrice,
                    10,
                    20,
                    30,
                    10,
                    false,
                    "0",
                    receiver1.getTelNumber(),
                    locker.getIdentityNumber()));
    }
}
