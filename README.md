# ACME Travel Back-end
Back-end part of a small project that shows how to integrate Spring Boot Application with Security Enabled.

## Build Results
![Uptime Check One](https://github.com/davidema79/airlines-poc/actions/workflows/uptimecheck-one.yml/badge.svg)
![Uptime Chcek Two - Fail](https://github.com/davidema79/airlines-poc/actions/workflows/uptimecheck-two.yml/badge.svg)

## Build
Build with the command:
```shell
	mvn clean install
```
It will execute the tests and generate an executable **fat** jar.

## Execute
The build result is an executable .jar file: `acme-travel.jar`, inside the directory acme-main/target/.

```shell
	java -jar acme-main\target\acme-travel.jar
``` 

## API
### RAML
The API is documented in a proper `Rest API Model Language` file, in the main directory.

### SWAGGER
If you prefer to see a graphical representation of the API, you can use the user interface you are provided with, at the url `http://localhost:8080/swagger-ui.html` 
