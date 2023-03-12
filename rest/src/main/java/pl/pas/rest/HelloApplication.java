package pl.pas.rest;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.ports.outcoming.UserRepository;

@ApplicationPath("/api")
public class HelloApplication extends Application {

    @Inject
    private UserRepository userRepository;

    @PostConstruct
    void init() {
        Client admin = new Client("admin", "admin", "admin");
        userRepository.add(admin);
    }
}
