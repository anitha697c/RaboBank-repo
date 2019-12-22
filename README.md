# Rabobank Customer Statement Processor
Rabobank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records needs to be validated.

## Input
The format of the file is a simplified format of the MT940 format. The format is as follows:

Field  |Description
----|----
Transaction reference  | A numeric value
Account number   | An IBAN 
Account | IBAN 
Start Balance | The starting balance in Euros 
Mutation | Either and addition (+) or a deducation (-) 
Description | Free text 
End Balance | The end balance in Euros 

## Expected output
There are two validations:
* all transaction references should be unique
* the end balance needs to be validated

At the end of the processing, a report needs to be created which will display both the transaction reference and description of each of the failed records.

# Technologies used
* Java 8
* Spring Boot 
     - Spring Boot Web 
     - Spring Boot Actuator
* Maven
* Mockito/junit
* Jsp

# Steps to run the application:
1. Clone the project Rabobank (Spring boot project).
2. Run maven command to clean and install and generate war file.
3. Set java path in cmd prompt and run the war using java -jar (filepath)/rabobank-0.0.1-SNAPSHOT.war
4. As it is spring boot application, no need to deploy it in the web server. You can simply run using 
   java -jar command
5. The base url is http://localhost:8080/rabobank/upload where you can a upload a file and submit for validation.
6. As the project is enabled with spring boot actuator, you can monitor the health of the application using  http://localhost:8080/actuator/health 
 http://localhost:8080/actuator/info etc
7. The input file will be validated based on two conduction mentioned in the problem statment.
   * Duplicate Transaction key check,
   * End balance calculation check. (endbalance = startbalance - mutation)
8. Invalid records with reference,description and failure reason will be displayed along with http code and response message.

### Exceptions Handled :
Handled the following issues,
* Only Csv,Xml file can be Uploaded. Other file format will not be suported.
* Mandatory input check.
* Application runtime exception also handled using @ExceptionHandler

### Test Cases:
Created test cases for all service classes in the application and it is in src/test/java/com/rabobank/testcase/RaboBankTestCases.java

#### Developed by [Anitha C](https://github.com/anitha697c)
