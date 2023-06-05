package pl.pas.as.core.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import pl.pas.as.core.model.exceptions.ClientManagerException;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.ports.incoming.UserService;
import pl.pas.as.ports.outcoming.DomainServiceCaller;
import pl.pas.as.ports.outcoming.UserRepository;
import pl.pas.as.ports.outcoming.dto.DomainClientDto;

@Log
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository clientRepository;
    private DomainServiceCaller domainServiceCaller;

    @Override
    public Client getUser(final String telNumber) {
        var client = clientRepository.findByTelNumber(telNumber);

        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ClientManagerException("Client with given telephone number not found");
        }
    }

    @Override
    public Optional<Client> getUserByTelNumber(final String telNumber) {
        return clientRepository.findByTelNumber(telNumber);
    }

    @Override
    public List<Client> getUsersByPartialTelNumber(final String telNumberPart) {
        return clientRepository.findByTelNumberPart(telNumberPart);
    }

    @Override
    public synchronized Client registerClient(final String firstName, final String lastName, final String telNumber, final String password, final String token) {
        for (Client user : clientRepository.findAll()) {
            if (user.getTelNumber().equals(telNumber))
                throw new ClientManagerException("Client with given telephone number already exits");
        }

        Client newUser = new Client(firstName, lastName, telNumber, password);
        clientRepository.add(newUser);
        log.info("client's been saved in AS-db");
        domainServiceCaller.registerClientInDomain(DomainClientDto.builder()
            .firstName(firstName)
            .lastName(lastName)
            .telNumber(telNumber)
            .build(),
            token);
        return newUser;
    }

    @Override
    public Client unregisterClient(final Client user, final String token) {
        if (user == null)
            throw new ClientManagerException("Client is a null!");

        getUser(user.getTelNumber());
        clientRepository.archive(user.getId());
        domainServiceCaller.unregisterClientInDomain(user.getTelNumber(), token);
        return user;
    }
}
