package pl.pas.as.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import pl.pas.as.rest.initializer.AuthServerApplicationInitializer;


@SpringBootApplication
public class SpringBootApplicationStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootApplicationStarter.class)
            .initializers(new AuthServerApplicationInitializer())
            .run(args);
    }
}
