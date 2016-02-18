package com.eharmony.services.swagger.persistence;

import com.eharmony.services.swagger.model.Documentation;

import java.util.List;

/**
 * Interface for defining the operations the documentation respository has.
 */
public interface DocumentationRepository {
    Documentation getDocumentationById(String id);

    void removeDocumentationById(String id) throws Exception;

    void saveDocumentation(Documentation documentation) throws Exception;

    List<Documentation> getDocumentationForAllServices();

    Documentation getDocumentationByServiceNameAndEnvironment(String serviceName, String environment);
}
