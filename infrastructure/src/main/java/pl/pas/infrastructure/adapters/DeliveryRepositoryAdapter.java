package pl.pas.infrastructure.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.adapters.mappers.ClientMapper;
import pl.pas.infrastructure.adapters.mappers.DeliveryMapper;
import pl.pas.infrastructure.adapters.mappers.DeliveryStatusMapper;
import pl.pas.infrastructure.model.delivery.DeliveryEnt;
import pl.pas.infrastructure.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.ports.outcoming.DeliveryRepository;

import java.util.List;
import java.util.UUID;

@Named
@ApplicationScoped
@NoArgsConstructor
public class DeliveryRepositoryAdapter implements DeliveryRepository {

    @Inject
    private DeliveryRepositoryHibernate deliveryRepository;

    @Override
    public Delivery get(UUID deliveryId) {
        DeliveryEnt deliveryEnt = deliveryRepository.get(deliveryId);
        return DeliveryMapper.mapToDomain(deliveryEnt);
    }

    @Override
    public void add(Delivery delivery) {
        DeliveryEnt deliveryEnt = DeliveryMapper.mapToEntity(delivery);
        deliveryRepository.add(deliveryEnt);
    }

    @Override
    public void update(Delivery delivery) {
        DeliveryEnt deliveryEnt = deliveryRepository.get(delivery.getId());
        deliveryEnt.setArchived(delivery.isArchived());
        deliveryEnt.setStatus(DeliveryStatusMapper.mapToEntity(delivery.getStatus()));
        deliveryEnt.setAllocationStart(delivery.getAllocationStart());
        deliveryEnt.setAllocationStop(delivery.getAllocationStop());
        deliveryRepository.update(deliveryEnt);
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
