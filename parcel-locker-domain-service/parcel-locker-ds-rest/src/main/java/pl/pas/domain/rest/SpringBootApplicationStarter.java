package pl.pas.domain.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import pl.pas.domain.rest.initializer.DomainApplicationInitializer;


@SpringBootApplication
public class SpringBootApplicationStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootApplicationStarter.class)
            .initializers(new DomainApplicationInitializer())
            .run(args);
    }

}

