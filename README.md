# Read Me First
The following was discovered as part of building this project:

* fixer.io external service allows only 100 requests under free account,
* at this time, it is only 16 requests left

# Getting Started

### Reference Documentation
For further reference, run application locally and consider the following sections:

* [Documentation](http://localhost:8080/swagger-ui/index.html)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Testcontainers support

This project uses [Testcontainers at development time](https://docs.spring.io/spring-boot/docs/3.2.4/reference/html/features.html#features.testing.testcontainers.at-development-time).

Testcontainers has been configured to use the following Docker images:

* [`postgres:`16.2](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.

