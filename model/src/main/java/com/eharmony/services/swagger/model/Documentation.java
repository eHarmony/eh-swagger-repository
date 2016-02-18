package com.eharmony.services.swagger.model;

/**
 * This is a meta data model for the swagger documentation server. This is shared between the client and server
 */
public class Documentation {
    private String id;
    private String serviceName;
    private String serviceDescription;
    private String serviceHost;
    private String swaggerUiUrl;
    private String swaggerSpecUrl;
    private String environment;
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public String getSwaggerUiUrl() {
        return swaggerUiUrl;
    }

    public void setSwaggerUiUrl(String swaggerUiUrl) {
        this.swaggerUiUrl = swaggerUiUrl;
    }

    public String getSwaggerSpecUrl() {
        return swaggerSpecUrl;
    }

    public void setSwaggerSpecUrl(String swaggerSpecUrl) {
        this.swaggerSpecUrl = swaggerSpecUrl;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

}
