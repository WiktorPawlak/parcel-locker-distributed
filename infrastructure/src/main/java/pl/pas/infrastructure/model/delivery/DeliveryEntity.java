package pl.pas.infrastructure.model.delivery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pas.infrastructure.model.EntityModel;
import pl.pas.infrastructure.model.user.ClientEntity;

@Entity
@Table(name = "DELIVERIES")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type")
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity extends EntityModel {

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "shipper_id")
    private ClientEntity shipper;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "receiver_id")
    private ClientEntity receiver;

    private DeliveryStatusEntity status;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pack_ID")
    private PackageEntity pack;

    @EqualsAndHashCode.Exclude
    private UUID lockerId;

    private LocalDateTime allocationStart;
    private LocalDateTime allocationStop;
    private boolean isArchived;


    public DeliveryEntity(UUID id,
                          Long version,
                          ClientEntity shipper,
                          ClientEntity receiver,
                          DeliveryStatusEntity status,
                          PackageEntity pack,
                          UUID lockerId,
                          LocalDateTime allocationStart,
                          LocalDateTime allocationStop,
                          boolean isArchived) {
        super(id, version);
        this.shipper = shipper;
        this.receiver = receiver;
        this.status = status;
        this.pack = pack;
        this.lockerId = lockerId;
        this.allocationStart = allocationStart;
        this.allocationStop = allocationStop;
        this.isArchived = isArchived;
    }

    public DeliveryEntity(BigDecimal basePrice,
                          boolean isPriority,
                          ClientEntity shipper,
                          ClientEntity receiver,
                          UUID lockerId,
                          Long version) {
        this(shipper, receiver, lockerId, version);

        this.pack = new ListEntity(UUID.randomUUID(), 0L, basePrice, isPriority);
    }

    private DeliveryEntity(ClientEntity shipper,
                           ClientEntity receiver,
                           UUID lockerId,
                           Long version) {
        this.id = UUID.randomUUID();
        this.version = version;
        this.shipper = shipper;
        this.receiver = receiver;
        this.lockerId = lockerId;
        this.status = DeliveryStatusEntity.READY_TO_SHIP;
    }
}
