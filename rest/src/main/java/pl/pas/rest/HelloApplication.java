package pl.pas.rest;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import pl.pas.core.applicationmodel.model.user.Administrator;
import pl.pas.core.applicationmodel.model.user.User;
import pl.pas.ports.outcoming.UserRepository;

@ApplicationPath("/api")
public class HelloApplication extends Application {

    @Inject
    private UserRepository userRepository;

    @PostConstruct
    void init() {
        User admin = new Administrator("admin", "admin", "admin");
        userRepository.add(admin);
    }
}
