package pl.pas.as.rest;

import java.util.Collections;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.ApplicationPath;
import pl.pas.as.rest.controllers.ClientController;

@ApplicationPath("/api")
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ClientController.class);
        setProperties(Collections.singletonMap("jersey.config.server.response.setStatusOverSendError", true));
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }

}
