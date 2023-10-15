# QR Code Class Scheduler

This is a straightforward Spring Boot application designed for managing class activities scheduling using QR codes.

## Building and Running

To build and run the application, follow these commands:
```
mvn clean instal
mvn spring-boot:run
```

To run the application with the dev profile:

```
mvn -Dspring-boot.run.profiles=dev spring-boot:run
```
To run the application with the prd profile:

```
mvn -Dspring-boot.run.profiles=prd spring-boot:run
```

## Profiles

    dev Profile: When the dev profile is enabled, QR codes are not encoded.
    prd Profile: For the prd profile, classroom IDs are encoded as QR codes.

## Usage/Examples

Sample requests are available in the requests.http file, which can be executed directly from IntelliJ.

## Considerations
### Ensuring Transactional Integrity

The application implements a 2-phase transaction for check-ins, ensuring transactional integrity. 
This ensures that the check-in is performed by the same person who made the retrieval, and requests are not forged. 
To achieve this, the endpoint /api/v1/activities returns a list of activities in a given classroom with a QR code for 
each activity. Activity QR codes include a *JSESSION* token retrieved during the authentication process. 
During registration, the *JSESSION* token is retrieved from the QR code and compared with the *JSESSION* token of the 
logged-in user, ensuring that the transaction is secure.

### Preventing QR Code Abuse

To prevent QR code abuse, such as taking a picture of the QR code and using it outside the classroom, 
the application employs a time-based validity mechanism. Registration QR codes are valid for a specific amount 
of time, thereby restricting their use to the intended purpose and time frame.

Feel free to further enhance the README as needed and include additional details specific to your project.




