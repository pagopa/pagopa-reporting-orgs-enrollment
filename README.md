# Reporting Organization Enrollment

This project is a Spring Boot application for the voluntary organizations enrollment to the reporting service

This service is called by [ReportingOrgsEnrollment](https://github.com/pagopa/pagopa-reporting-orgs-enrollment) 

---
## Api Documentation 📖

See the [OpenApi 3 here.](https://editor.swagger.io/?url=https://raw.githubusercontent.com/pagopa/pagopa-reporting-orgs-enrollment/main/openapi/openapi.json)

In local env typing following url on browser for ui interface:

```
http://localhost:8080/swagger-ui/index.html

```
or that for `json` version
```http://localhost:8080/v3/api-docs/```

---

## Technology Stack 📚
- Java 11
- Spring Boot
- Spring Web
- [Azure Table Storage API](https://learn.microsoft.com/en-us/java/api/overview/azure/data-tables-readme?view=azure-java-stable)

---


# How to run Locally 🚀
### Prerequisites
- Azure Storage Explorer

### Run the project
The easiest way to run locally is start the Microsoft Storage Explorer and then run the spring-boot application:

- Install Azurite

```
Example with npm:
npm install -g azurite

Exmple with docker: 
docker pull mcr.microsoft.com/azure-storage/azurite
```

- Run Azurite

```
Example with npm:
azurite --silent --location c:\azurite --debug c:\azurite\debug.log

Exmple with docker: 
docker run -p 10000:10000 -p 10001:10001 -p 10002:10002 \
    mcr.microsoft.com/azure-storage/azurite
```

- Run the application

```
Navigate to the root of the project via command line and execute the command:
mvn spring-boot:run
```


---

# Run Tests 🧪

#### Unit test

Typing `mvn clean verify`

#### Integration test

- Run the application
- Install dependencies: `yarn install`
- Run the test: `yarn test`

---

## Contributors 👥
Made with ❤️ by PagoPa S.p.A.

### Mainteiners
See `CODEOWNERS` file


