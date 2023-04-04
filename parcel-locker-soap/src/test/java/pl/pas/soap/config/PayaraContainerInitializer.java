package pl.pas.soap.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PayaraContainerInitializer {

    private static final String APP_NAME = "parcel-locker-soap-1.0-SNAPSHOT";

    public static final String DB_NAME = "database";
    public static final String DB_USERNAME = "admin";
    public static final String DB_PASSWORD = "admin";
    public static final DockerImageName POSTGRES_IMAGE = DockerImageName
        .parse("postgres")
        .withTag("15.0-alpine");

    private static final int PORT = 8080;
    private static final String PACKAGE_NAME = APP_NAME + ".war";
    private static final String CONTAINER_DEPLOYMENT_PATH = "/opt/payara/deployments/";
    private static final DockerImageName PAYARA_IMAGE = DockerImageName
        .parse("payara/server-full")
        .withTag("6.2023.3-jdk17");

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
        .withNetwork(network)
        .dependsOn(postgres)
        .waitingFor(Wait.forHttp("/" + APP_NAME + "/clientsSoapApi?wsdl").forPort(8080).forStatusCode(200));

    protected static String baseUri;

    @BeforeAll
    protected void setup() {
        if (!postgres.isRunning())
            postgres.start();
        if (!jakartaApp.isRunning())
            jakartaApp.start();

        baseUri = "http://" +
            jakartaApp.getHost() + ":" +
            jakartaApp.getMappedPort(PORT) +
            "/" + APP_NAME;
    }
}
