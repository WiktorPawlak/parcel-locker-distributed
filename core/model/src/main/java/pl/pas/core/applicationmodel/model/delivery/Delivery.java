package pl.pas.core.applicationmodel.model.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pas.core.applicationmodel.model.EntityModel;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Delivery {

    private UUID id;
    @EqualsAndHashCode.Exclude
    private Client shipper;

    @EqualsAndHashCode.Exclude
    private Client receiver;
    private DeliveryStatus status;

    @EqualsAndHashCode.Exclude
    private Package pack;

    @EqualsAndHashCode.Exclude
    private Locker locker;

    private LocalDateTime allocationStart;
    private LocalDateTime allocationStop;
    private boolean isArchived;

    public Delivery(BigDecimal basePrice,
                    double width,
                    double length,
                    double height,
                    double weight,
                    boolean isFragile,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        this(shipper, receiver, locker);

        this.pack = new Parcel(basePrice, width, length, height, weight, isFragile);
    }

    public Delivery(BigDecimal basePrice,
                    boolean isPriority,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        this(shipper, receiver, locker);

        this.pack = new List(basePrice, isPriority);
    }

    private Delivery(Client shipper,
                     Client receiver,
                     Locker locker) {
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;
        this.status = DeliveryStatus.READY_TO_SHIP;
    }

    public BigDecimal getCost() {
        return pack.getCost();
    }

    public Client getShipper() {
        return shipper;
    }

    public Client getReceiver() {
        return receiver;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public Package getPack() {
        return pack;
    }

    public Locker getLocker() {
        return locker;
    }

    public LocalDateTime getAllocationStop() {
        return allocationStop;
    }

    public void setAllocationStop(LocalDateTime allocationStop) {
        this.allocationStop = allocationStop;
    }

    public LocalDateTime getAllocationStart() {
        return allocationStart;
    }

    public void setAllocationStart(LocalDateTime allocationStart) {
        this.allocationStart = allocationStart;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

}
