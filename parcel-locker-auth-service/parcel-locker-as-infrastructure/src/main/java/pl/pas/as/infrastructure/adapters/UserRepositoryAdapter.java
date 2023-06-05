package pl.pas.as.infrastructure.adapters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.infrastructure.adapters.mappers.ClientMapper;
import pl.pas.as.infrastructure.model.user.ClientEntity;
import pl.pas.as.infrastructure.repositories.hibernate.UserRepositoryHibernate;
import pl.pas.as.ports.outcoming.UserRepository;

@Named
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private UserRepositoryHibernate userRepository;

    @Override
    public void add(Client user) {
        ClientEntity userEnt = ClientMapper.mapToEntity(user);
        userRepository.add(userEnt);
    }

    @Override
    public void archive(UUID clientId) {
        userRepository.archive(clientId);
    }

    @Override
    public List<Client> findAll() {
        return userRepository.findAll().stream()
            .map(ClientMapper::mapToDomain)
            .toList();
    }

    @Override
    public Optional<Client> findByTelNumber(String telNumber) {
        return userRepository.findByTelNumber(telNumber)
            .map(ClientMapper::mapToDomain);
    }

    @Override
    public List<Client> findByTelNumberPart(String telNumberPart) {
        return userRepository.findByTelNumberPart(telNumberPart).stream()
            .map(ClientMapper::mapToDomain)
            .toList();
    }

    @Override
    public Optional<Client> findUserById(UUID uuid) {
        return userRepository.findUserById(uuid)
            .map(ClientMapper::mapToDomain);
    }
}
