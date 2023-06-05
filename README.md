# parcel-locker-tks-2023 [![Java CI with Maven](https://github.com/o-trela/parcel-locker-tks-2023/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/o-trela/parcel-locker-tks-2023/actions/workflows/maven.yml)
REST application implementing opinionated logic of parcel lockers.

## Requirements
| Name          | Version                 |
|:--------------|:------------------------|
| JDK           | Eclipse temurin 17.0.16 |
| Apache Tomcat | 10.8.1                  |
| Spring Boot   | 3.0.7                   |
| postgres      | 15.0-alpine             |
| Grafana       | 9.3.6                   |
| Loki          | 2.7.3                   |
| Prometheus    | 2.42.0                  |
| Tempo         | 2.0.0                   |

## Architecture

This project's requirement was to distillate microservices from previous modular monolith (tag Ports&Adapters distillation).\
There are five microservices, each implemented as maven module:

| Service           | Responsibility                           |
|:------------------|:-----------------------------------------|
| api-gateway       | entry point, request filtering, security |
| auth-service      | owner of user accounts, registering      |
| domain-service    | parcel locker business logic             |
| discovery-service | microservice registry, observability     |

Another requirement was that every microservice containing logic should have its own database.\
Therefore, after persisting account during registration process, auth-server calls domain microservice\
to persist account in its local database as well.

Auth-server's and domain's were initially implemented using Jakarta 10 (notice previous tags).\
In order achieve this stage of the project, development team decided to migrate to Spring Boot.\
To reduce the invasiveness of migration changes, JAX-RS web model was preserved thanks to its reference implementation - spring-boot-starter-jersey.

## Observability
Application has been integrated with observability stack (Grafana, Loki, Prometheus, Tempo).
By entering Grafana (http://localhost:3000) you are able to look up every feature that our observability stack provides.

### Grafana
It's data-visualization platform which aggregates features of all our observability tools.
Repository contains prepared dashboard configuration for API Gateway metrics which will be accessible from Grafana.
After entering Explore tab you can choose between logs, traces and metrics.

### Loki
Tool used for gathering logs from services. Loki is integrated with Tempo, so you are able to see traces after clicking on specific log.

### Prometheus
It's gathering metrics from services by retrieving them from actuator's endpoints (`/actuator/prometheus`).
The only metrics visualized in Grafana are for API Gateway.
Prometheus is configurated to gather metrics from all services so it's possible to create new dashboards for each of them.

### Tempo
Some of the logs contain `traceId` and `spanId`. This helps Tempo with following the trace by connecting distributed logs with the same `traceId`.
In Grafana we are able to follow traces from Loki's page by just clicking on log with `traceId` or by choosing Tempo as tool in Explore tab.
Tempo also gathers time metrics so it's possible to check how long the request took.

## Startup
Before running applications it's important to change `jakarta.persistence.jdbc.url` in two persistance.xml files
([persistance.xml (domain-service)](https://github.com/o-trela/parcel-locker-tks-2023/blob/master/parcel-locker-domain-service/parcel-locker-ds-infrastructure/src/main/resources/META-INF/persistence.xml)
& [persistance.xml (auth-service)](https://github.com/o-trela/parcel-locker-tks-2023/blob/master/parcel-locker-auth-service/parcel-locker-as-infrastructure/src/main/resources/META-INF/persistence.xml))
accordingly to comments.

In the root of the project there is a docker-compose.yaml for postgresql and observability deployment.
Before starting Tomcat server it needs to be executed.

```shell
docker compose up
```

Integration tests are wrapped with Test Containers.\
This means docker-compose does not need to be executed.\
Only `dockerd` needs to be running.

.run directory contains necessary configuration for application startup and testing.\
Remember to always rebuild whole project before startup/tests!\
Prepared configuration does this for you.

# Tests
Before running tests it's important to change `jakarta.persistence.jdbc.url` in two persistance.xml files
([persistance.xml (domain-service)](https://github.com/o-trela/parcel-locker-tks-2023/blob/master/parcel-locker-domain-service/parcel-locker-ds-infrastructure/src/main/resources/META-INF/persistence.xml)
& [persistance.xml (auth-service)](https://github.com/o-trela/parcel-locker-tks-2023/blob/master/parcel-locker-auth-service/parcel-locker-as-infrastructure/src/main/resources/META-INF/persistence.xml))
accordingly to comments.
```shell
mvn test
```
