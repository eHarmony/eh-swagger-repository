package com.eharmony.services.swagger.model;

import java.util.List;

public class Dashboard {
    private String id;
    private String name;
    private String description;
    private List<String> documentationIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDocumentationIds() {
        return documentationIds;
    }

    public void setDocumentationIds(List<String> documentationIds) {
        this.documentationIds = documentationIds;
    }
}
