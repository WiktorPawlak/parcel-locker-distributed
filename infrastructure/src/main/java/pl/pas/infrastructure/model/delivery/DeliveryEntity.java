package pl.pas.infrastructure.model.delivery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.Hibernate;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.infrastructure.model.EntityModel;
import pl.pas.infrastructure.model.locker.LockerEntity;
import pl.pas.infrastructure.model.user.ClientEntity;

@Entity
@Table(name = "DELIVERIES")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type",
    discriminatorType = DiscriminatorType.INTEGER)
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity extends EntityModel {

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "shipper_id")
    private ClientEntity shipper;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "receiver_id")
    private ClientEntity receiver;

    private DeliveryStatusEntity status;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pack_ID")
    private PackageEntity pack;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "locker_id")
    private LockerEntity locker;

    private LocalDateTime allocationStart;
    private LocalDateTime allocationStop;
    private boolean isArchived;


    public DeliveryEntity(UUID id,
                          ClientEntity shipper,
                          ClientEntity receiver,
                          DeliveryStatusEntity status,
                          PackageEntity pack,
                          LockerEntity locker,
                          LocalDateTime allocationStart,
                          LocalDateTime allocationStop,
                          boolean isArchived) {
        super(id);
        this.shipper = shipper;
        this.receiver = receiver;
        this.status = status;
        this.pack = pack;
        this.locker = locker;
        this.allocationStart = allocationStart;
        this.allocationStop = allocationStop;
        this.isArchived = isArchived;
    }

    public DeliveryEntity(BigDecimal basePrice,
                          boolean isPriority,
                          ClientEntity shipper,
                          ClientEntity receiver,
                          LockerEntity locker) {
        this(shipper, receiver, locker);

        this.pack = new ListEntity(basePrice, isPriority);
    }

    private DeliveryEntity(ClientEntity shipper,
                           ClientEntity receiver,
                           LockerEntity locker) {
        this.id = UUID.randomUUID();
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;
        this.status = DeliveryStatusEntity.READY_TO_SHIP;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final DeliveryEntity that = (DeliveryEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
