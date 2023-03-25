package pl.pas.rest.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JakartaContainerInitializer {

    public static final String DB_NAME = "database";
    public static final String DB_USERNAME = "admin";
    public static final String DB_PASSWORD = "admin";
    public static final DockerImageName POSTGRES_IMAGE = DockerImageName
        .parse("postgres")
        .withTag("15.0-alpine");

    private static final int PORT = 8080;
    private static final String PACKAGE_NAME = "parcel-locker-rest-1.0-SNAPSHOT.war";
    private static final String CONTAINER_DEPLOYMENT_PATH = "/opt/payara/deployments/";
    private static final DockerImageName PAYARA_IMAGE = DockerImageName
        .parse("payara/micro")
        .withTag("6.2023.2-jdk17");

    private static final Network network = Network.newNetwork();

    static final PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD)
            .withNetwork(network)
            .withNetworkAliases("postgres");

    protected static GenericContainer<?> jakartaApp = new GenericContainer<>(PAYARA_IMAGE)
        .withExposedPorts(PORT)
        .withEnv("DB_HOST_PORT", "postgres:5432")
        .withCopyFileToContainer(
            MountableFile.forHostPath("target/" + PACKAGE_NAME),
            CONTAINER_DEPLOYMENT_PATH + PACKAGE_NAME)
        .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
        .withNetwork(network)
        .dependsOn(postgres)
        .withCommand("--deploy " + CONTAINER_DEPLOYMENT_PATH + PACKAGE_NAME + " --contextRoot /");

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    protected void setup() {
        if (!postgres.isRunning())
            postgres.start();
        if (!jakartaApp.isRunning())
            jakartaApp.start();

        String baseUri = "http://" +
            jakartaApp.getHost() + ":" +
            jakartaApp.getMappedPort(PORT);

        requestSpecification = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .build();
    }
}
