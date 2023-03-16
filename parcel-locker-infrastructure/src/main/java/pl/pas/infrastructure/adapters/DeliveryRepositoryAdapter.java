package pl.pas.infrastructure.adapters;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.adapters.mappers.ClientMapper;
import pl.pas.infrastructure.adapters.mappers.DeliveryMapper;
import pl.pas.infrastructure.adapters.mappers.DeliveryStatusMapper;
import pl.pas.infrastructure.model.delivery.DeliveryEntity;
import pl.pas.infrastructure.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.ports.outcoming.DeliveryRepository;


@ApplicationScoped
@NoArgsConstructor
public class DeliveryRepositoryAdapter implements DeliveryRepository {

    @Inject
    private DeliveryRepositoryHibernate deliveryRepository;

    @Override
    public Delivery get(UUID deliveryId) {
        DeliveryEntity deliveryEntity = deliveryRepository.get(deliveryId);
        return DeliveryMapper.mapToDomain(deliveryEntity);
    }

    @Override
    public void add(Delivery delivery) {
        DeliveryEntity deliveryEntity = DeliveryMapper.mapToEntity(delivery);
        deliveryRepository.add(deliveryEntity);
    }

    @Override
    public void update(Delivery delivery) {
        DeliveryEntity deliveryEntity = deliveryRepository.get(delivery.getId());
        deliveryEntity.setArchived(delivery.isArchived());
        deliveryEntity.setStatus(DeliveryStatusMapper.mapToEntity(delivery.getStatus()));
        deliveryEntity.setAllocationStart(delivery.getAllocationStart());
        deliveryEntity.setAllocationStop(delivery.getAllocationStop());
        deliveryRepository.update(deliveryEntity);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryRepository.findAll().stream()
            .map(DeliveryMapper::mapToDomain)
            .toList();
    }

    @Override
    public List<Delivery> findReceivedByClient(Client user) {
        return deliveryRepository.findReceivedByClient(ClientMapper.mapToEntity(user)).stream()
            .map(DeliveryMapper::mapToDomain)
            .toList();
    }

    @Override
    public List<Delivery> findCurrentByClient(Client user) {
        return deliveryRepository.findCurrentByClient(ClientMapper.mapToEntity(user)).stream()
            .map(DeliveryMapper::mapToDomain)
            .toList();
    }

    @Override
    public List<Delivery> findByUser(Client user) {
        return deliveryRepository.findByUser(ClientMapper.mapToEntity(user)).stream()
            .map(DeliveryMapper::mapToDomain)
            .toList();
    }

}
