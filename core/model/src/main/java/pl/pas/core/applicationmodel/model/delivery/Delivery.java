package pl.pas.core.applicationmodel.model.delivery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;


@Getter
@EqualsAndHashCode
public class Delivery {

    private final UUID id;

    private final Long version;

    @EqualsAndHashCode.Exclude
    private final Client shipper;

    @EqualsAndHashCode.Exclude
    private final Client receiver;

    @Setter
    private DeliveryStatus status;

    @EqualsAndHashCode.Exclude
    private Package pack;

    @EqualsAndHashCode.Exclude
    private final Locker locker;

    @Setter
    private LocalDateTime allocationStart;

    @Setter
    private LocalDateTime allocationStop;

    @Setter
    private boolean isArchived;

    public Delivery(final UUID id, final Long version,
                    final Client shipper, final Client receiver,
                    final DeliveryStatus status,
                    final Package pack, final Locker locker,
                    final LocalDateTime allocationStart, final LocalDateTime allocationStop,
                    final boolean isArchived) {
        this(id, version, shipper, receiver, locker);
        this.status = status;
        this.pack = pack;
        this.allocationStart = allocationStart;
        this.allocationStop = allocationStop;
        this.isArchived = isArchived;
    }

    public Delivery(BigDecimal basePrice,
                    double width,
                    double length,
                    double height,
                    double weight,
                    boolean isFragile,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        this(UUID.randomUUID(), 0L, shipper, receiver, locker);

        this.pack = new Parcel(basePrice, width, length, height, weight, isFragile);
    }

    public Delivery(BigDecimal basePrice,
                    boolean isPriority,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        this(UUID.randomUUID(), 0L, shipper, receiver, locker);

        this.pack = new List(basePrice, isPriority);
    }

    private Delivery(UUID id, Long version,
                     Client shipper,
                     Client receiver,
                     Locker locker) {
        this.id = id;
        this.version = version;
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;
        this.status = DeliveryStatus.READY_TO_SHIP;
    }

    public BigDecimal getCost() {
        return pack.getCost();
    }
}
