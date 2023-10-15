# QR Code Class Scheduler

Simple Spring Boot Application for class activities scheduling.

## Build and run

```
mvn clean instal
mvn spring-boot:run
```

To run `dev` profile:

```
mvn -Dspring-boot.run.profiles=dev spring-boot:run
```

To run `prd` profile:

```
mvn -Dspring-boot.run.profiles=prd spring-boot:run
```

## Profiles

When `dev` profile is enabled QR codes are not encoded.
For `prd` profile classroom ids are encoded as a QR codes.

## Usage/Examples

Sample requests are available in *requests.http* file and can be executed from Intellij.

## Assumptions




