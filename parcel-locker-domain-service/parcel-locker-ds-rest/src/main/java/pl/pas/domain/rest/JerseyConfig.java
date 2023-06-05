package pl.pas.domain.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.ApplicationPath;
import pl.pas.domain.rest.controllers.ClientController;
import pl.pas.domain.rest.controllers.DeliveryController;
import pl.pas.domain.rest.controllers.LockerController;

@ApplicationPath("/api")
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ClientController.class);
        register(DeliveryController.class);
        register(LockerController.class);
    }

}
