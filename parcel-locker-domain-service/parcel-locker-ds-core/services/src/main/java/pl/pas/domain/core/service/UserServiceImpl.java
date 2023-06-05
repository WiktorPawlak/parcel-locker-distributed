package pl.pas.domain.core.service;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import pl.pas.domain.core.applicationmodel.exceptions.ClientManagerException;
import pl.pas.domain.core.applicationmodel.model.user.Client;
import pl.pas.domain.ports.incoming.UserService;
import pl.pas.domain.ports.outcoming.UserRepository;


@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository clientRepository;

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
    public synchronized Client registerClient(String firstName, String lastName, String telNumber) {
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
    public Client unregisterClient(Client user) {
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
