package pl.pas.as.rest.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = AuthServerTestApplicationInitializer.class)
public class DbContainerInitializer {

    public static final String DB_NAME = "database";
    public static final String DB_USERNAME = "admin";
    public static final String DB_PASSWORD = "admin";
    public static final DockerImageName POSTGRES_IMAGE = DockerImageName
        .parse("postgres")
        .withTag("15.0-alpine");

    @LocalServerPort
    private Integer port;

    private static final Network network = Network.newNetwork();

    @Container
    static final PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD)
            .withNetwork(network)
            .withNetworkAliases("postgres");

    static {
        postgres.start();
        System.setProperty("db.port", postgres.getFirstMappedPort().toString());
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    protected void setup() {
        String baseUri = "http://localhost:" + port + "/";

        requestSpecification = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .build();
    }
}
