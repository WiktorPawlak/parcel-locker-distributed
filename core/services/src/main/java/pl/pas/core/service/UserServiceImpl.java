package pl.pas.core.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.exceptions.ClientManagerException;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.ports.outcoming.UserRepository;
import pl.pas.ports.incoming.UserService;


@ApplicationScoped
@NoArgsConstructor
public class UserServiceImpl implements UserService {


    @Inject
    private UserRepository clientRepository;

    public UserServiceImpl(UserRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getUser(String telNumber) {
        validateIfEmpty(telNumber);

        var client = clientRepository.findByTelNumber(telNumber);

        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ClientManagerException("Client with given telephone number not found");
        }
    }

    @Override
    public List<Client> getUsersByPartialTelNumber(String telNumberPart) {
        validateIfEmpty(telNumberPart);

        return clientRepository.findByTelNumberPart(telNumberPart);
    }

    @Override
    public synchronized Client registerClient(UUID operatorId, String firstName, String lastName, String telNumber) {
        validateIfEmpty(firstName, lastName, telNumber);

        for (Client user : clientRepository.findAll()) {
            if (user.getTelNumber().equals(telNumber))
                throw new ClientManagerException("Client with given telephone number already exits");
        }

        Client newUser = new Client(firstName, lastName, telNumber);
        clientRepository.add(newUser);
        return newUser;
    }

    @Override
    public Client unregisterClient(UUID operatorId, Client user) {
        if (user == null)
            throw new ClientManagerException("Client is a null!");

        getUser(user.getTelNumber());
        clientRepository.archive(user.getId());
        return user;
    }

    private void validateIfEmpty(String... values) {
        if (Arrays.stream(values).anyMatch(String::isEmpty))
            throw new ClientManagerException("Value is empty!");
    }
}
