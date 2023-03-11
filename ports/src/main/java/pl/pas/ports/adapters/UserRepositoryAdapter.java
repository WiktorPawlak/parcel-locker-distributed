package pl.pas.ports.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.pas.core.applicationmodel.model.user.User;
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
    public void add(User user) {

    }

    @Override
    public void archive(UUID clientId) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByTelNumber(String telNumber) {
        return Optional.empty();
    }

    @Override
    public List<User> findByTelNumberPart(String telNumberPart) {
        return null;
    }

    @Override
    public Optional<User> findUserById(UUID uuid) {
        return Optional.empty();
    }
}
