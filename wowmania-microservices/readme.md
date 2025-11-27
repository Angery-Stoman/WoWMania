WoWMania



WoWMania is a secure platform for World of Warcraft players to buy and sell services. This project implements the backend architecture using Java 21, Spring Boot, and Docker.



How we built it:



The system consists of three independent microservices:



1\.  User Service (Port 8081): Handles registration (Factory Pattern) and storage of Buyers/Sellers.

2\.  Marketplace Service (Port 8082): Manages product listings (Builder Pattern).

3\.  Order Service (Port 8083): Handles transactions and utilizes the Observer Pattern for status updates. It communicates with the Marketplace Service via REST APIs to verify listings.



Prerequisites to run it:



\* Java 21 (JDK)

\* Maven

\* Docker Desktop (Must be running)



How to actually run the project:



Step 1: Build the code

Before running Docker, you must compile the Java code.

1\.  Open the project in IntelliJ IDEA (that is what we used, didn't test it out on other code editors)

2\.  Open the Maven sidebar on the right side.

3\.  Expand the wowmania-microservices (root) folder, open Lifecycle and double-click Clean.

4\.  Run Package the same way (double-click).

5\.  Ensure all 3 services show "BUILD SUCCESS".



Step 2: Run with Docker

1\.  Open a terminal in the project root folder.

2\.  Run the following command:



&nbsp;   docker-compose up --build



3\.  Wait until the logs stop scrolling. The services are now active on ports 8081, 8082, and 8083.



Step 3: Testing with Postman



A Postman collection (`wowmania\_test.json`) is included in this submission.



1\.  Open Postman.

2\.  Either press CTRL + o or click the 3 lines at the top left, File -> Import. Now select the `.json` file.

3\.  Follow the numbered requests in order (1 to 5).

&nbsp;   \* Note: You must copy the `ID` generated in the response of previous steps and paste them into the "Body" of the subsequent steps where we placed placeholders like `REPLACE\_WITH\_ID`.



Project Structure



\* /user-service: Authentication and user management

\* /marketplace-service: Listing management

\* /order-service: Order processing and inter-service communication

\* docker-compose.yml: Orchestration configuration for all services

