package pl.pas.infrastructure.model.delivery;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pas.infrastructure.model.EntityModel;
import pl.pas.infrastructure.model.locker.LockerEnt;
import pl.pas.infrastructure.model.user.ClientEnt;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type",
    discriminatorType = DiscriminatorType.INTEGER)
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DeliveryEnt extends EntityModel {

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shipper_id")
    private ClientEnt shipper;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "receiver_id")
    private ClientEnt receiver;
    private DeliveryStatusEnt status;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pack_ID")
    private PackageEnt pack;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "locker_id")
    private LockerEnt locker;

    private LocalDateTime allocationStart;
    private LocalDateTime allocationStop;
    private boolean isArchived;

    public DeliveryEnt(BigDecimal basePrice,
                       double width,
                       double length,
                       double height,
                       double weight,
                       boolean isFragile,
                       ClientEnt shipper,
                       ClientEnt receiver,
                       LockerEnt locker) {
        this(shipper, receiver, locker);

        this.pack = new ParcelEnt(basePrice, width, length, height, weight, isFragile);
    }

    public DeliveryEnt(BigDecimal basePrice,
                       boolean isPriority,
                       ClientEnt shipper,
                       ClientEnt receiver,
                       LockerEnt locker) {
        this(shipper, receiver, locker);

        this.pack = new ListEnt(basePrice, isPriority);
    }

    private DeliveryEnt(ClientEnt shipper,
                        ClientEnt receiver,
                        LockerEnt locker) {
        this.id = UUID.randomUUID();
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;
        this.status = DeliveryStatusEnt.READY_TO_SHIP;
    }

    public BigDecimal getCost() {
        return pack.getCost();
    }

    public ClientEnt getShipper() {
        return shipper;
    }

    public ClientEnt getReceiver() {
        return receiver;
    }

    public DeliveryStatusEnt getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatusEnt status) {
        this.status = status;
    }

    public PackageEnt getPack() {
        return pack;
    }

    public LockerEnt getLocker() {
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
