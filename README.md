# eh-swagger-repository  [![Build Status](https://travis-ci.org/eHarmony/eh-swagger-repository.svg?branch=master)](https://travis-ci.org/eHarmony/eh-swagger-repository)

The central documentation server for finding swagger enabled services. Swagger enabled services should use
```eh-swagger-repository-client``` to automatically publish their swagger specs to this service.

## About

This service allows swagger enabled web services to publish their Swagger spec and other Swagger metadata to this
web server.
* /dashboards : Create and manage groupings of services as dashboards
* /search : The table view of all services that allows you to see more information about each service and search
* /dashboards/my : The dashboard that allows you to view, save and search different swagger UIs from available services

## Configure

The only configuration needed for this is specifying where you want the file to be saved for persisting the service
documentation.

```
filerepository.location: /usr/local
```

## Run Locally

```
mvn jetty:run
```

http://localhost:8081/
