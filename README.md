eh-swagger-repository
=====================

The central documentation server for finding swagger enabled services. Swagger enabled services should use
```eh-swagger-repository-client``` to automatically publish their swagger specs to this service.

## About

This service allows swagger enabled web services to publish their Swagger spec and other Swagger metadata to this
web server.
* /index.html : The dashboard that allows you to view, save and search different swagger UIs from available services
* /services.html : The table view of all services that allows you to see more information about each service and search

## Configure

The only configuration needed for this is specifying where you want the file to be saved for persisting the service
documentation.

```yaml
    filerepository_location: /usr/local
```

## Run Locally

```
    mvn jetty:run
```
http://localhost:8081/