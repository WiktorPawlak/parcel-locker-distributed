# parcel-locker-tks-2023 [![Java CI with Maven](https://github.com/o-trela/parcel-locker-tks-2023/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/o-trela/parcel-locker-tks-2023/actions/workflows/maven.yml)
REST application implementing opinionated logic of parcel lockers.\
It also exposes CRUD Soap API.\
Port&Adapters approach was chosen as an architecture of the project.

## Requirements

| Name                                  | Version                            |
| :---                                  | :---                               |
| JDK                                   | Eclipse temurin 17.0.16            |
| Payara Server                         | Community Edition 6.2023.2 (full)  |
| IntelliJ Payara Platform Tools plugin | 1.5.0                              |
| postgres                              | 15.0-alpine                        |

## Startup

In the root of the project there is a docker-compose.yaml for postgresql deployment.
Before starting payara server it needs to be executed.

Integration tests are wrapped with Test Containers.\
This means docker-compose does not need to be executed.\
Only dockerd needs to be running.

.run directory contains necessary configuration for application startup and testing.\
Remember to always rebuild whole project before startup/tests!\
Prepared configuration does this for you.
