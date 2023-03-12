package pl.pas.infrastructure.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.repositories.hibernate.UserRepositoryHibernate;
import pl.pas.ports.outcoming.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepository {

    @Inject
    private UserRepositoryHibernate userRepository;

    @Override
    public void add(Client user) {

    }

    @Override
    public void archive(UUID clientId) {

    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Optional<Client> findByTelNumber(String telNumber) {
        return Optional.empty();
    }

    @Override
    public List<Client> findByTelNumberPart(String telNumberPart) {
        return null;
    }

    @Override
    public Optional<Client> findUserById(UUID uuid) {
        return Optional.empty();
    }
}
