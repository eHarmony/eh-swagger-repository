/*
 * Copyright 2016 eHarmony, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eharmony.services.swagger.resource;

import com.eharmony.services.swagger.model.Documentation;
import com.eharmony.services.swagger.persistence.DocumentationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/1.0/documentation")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Api(value = "Documentation Respository Resource")
public class DocumentationRepositoryResource {
    private static final Log LOG = LogFactory.getLog(DocumentationRepositoryResource.class);

    @Autowired
    DocumentationRepository repository;

    /**
     * Returns a list of documentation for all services, sorted by service name.
     */
    @GET
    @ApiOperation(value = "Get All Documentation",
            notes = "Returns a list of documentation for all services, sorted by service name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The list of all documentation sorted by service name",
                    response = Documentation.class,
                    responseContainer = "List"),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the list of documentation")
            })
    public Response getAllDocumentation() {
        try {
            List<Documentation> documentation = repository.getDocumentationForAllServices();
            return Response.ok().entity(documentation).build();
        } catch (Exception ex) {
            LOG.error("Unable to retrieve documentation", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Saves a services documentation. Validates all required fields. If the same service name and
     * environment is found, it is overwritten.
     * @param documentation The unique documentation id.
     */
    @POST
    @ApiOperation(value = "Save documentation",
            notes = "Saves a services documentation. Validates all required fields. If the same service name and " +
                    "environment is found, it is overwritten.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The documentation was overwritten successfully."),
            @ApiResponse(code = 201, message = "The documentation was created successfully."),
            @ApiResponse(code = 400, message = "A required field is missing."),
            @ApiResponse(code = 500, message = "An error occurred when attempting to save the documentation.")
            })
    public Response saveDocumentationForService(
            @ApiParam(value = "A single service documentation object") Documentation documentation) {
        try {
            validateDocumentation(documentation);
            Documentation existingDocumentation = repository.getDocumentationByServiceNameAndEnvironment(
                    documentation.getServiceName(), documentation.getEnvironment());
            if (existingDocumentation != null) {
                documentation.setId(existingDocumentation.getId());
                repository.saveDocumentation(documentation);
                return Response.ok().build();
            } else {
                repository.saveDocumentation(documentation);
                return Response.status(201).build();
            }
        } catch (IllegalArgumentException iae) {
            return Response.status(400).entity(iae.getMessage()).build();
        } catch (Exception ex) {
            LOG.error("Unable to save documentation", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Retrieves a services documentation by id.
     * @param documentationId The unique documentation id.
     */
    @GET
    @Path(value = "/{id}")
    @ApiOperation(value = "Find documentation by id",
            notes = "Retrieves a services documentation by id.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The documentation was found for the given id.",
                    response = Documentation.class),
            @ApiResponse(code = 404, message = "The documentation was not found for that id."),
            @ApiResponse(code = 500, message = "An error occurred when attempting to retrieve the documentation.")
            })
    public Response getDocumentationById(
            @PathParam(value = "id") @ApiParam(value = "The unique documentation id.") String documentationId) {
        try {
            Documentation documentation = repository.getDocumentationById(documentationId);
            if (documentation == null) {
                return Response.status(404).build();
            }
            return Response.ok().entity(documentation).build();
        } catch (Exception ex) {
            LOG.error("Unable to retrieve documentation", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Remove documentation by id.
     * @param documentationId The unique documentation id.
     */
    @DELETE
    @Path(value = "/{id}")
    @ApiOperation(value = "Remove documentation",
            notes = "Removes a services documentation by id.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The documentation was removed successfully."),
            @ApiResponse(code = 404, message = "The documentation was not found for that id."),
            @ApiResponse(code = 500, message = "An error occurred when attempting to remove the documentation.")
            })
    public Response removeDocumentationForService(
            @PathParam(value = "id") @ApiParam(value = "The unique documentation id.") String documentationId) {
        try {
            if (repository.getDocumentationById(documentationId) == null) {
                return Response.status(404).build();
            }
            repository.removeDocumentationById(documentationId);
            return Response.ok().build();
        } catch (Exception ex) {
            LOG.error("Unable to remove documentation", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Validates required fields on the documentation instance.
     * @param documentation Documentation to validate
     */
    private void validateDocumentation(Documentation documentation) {
        Validate.notEmpty(documentation.getCategory(), "Category is required");
        Validate.notEmpty(documentation.getEnvironment(), "Environment is required");
        Validate.notEmpty(documentation.getServiceDescription(), "Service description is required");
        Validate.notEmpty(documentation.getServiceName(), "Service Name is required");
        Validate.notEmpty(documentation.getSwaggerSpecUrl(), "Swagger Spec url is required");
        Validate.notEmpty(documentation.getSwaggerUiUrl(), "Swagger UI url is required");
        Validate.notEmpty(documentation.getServiceHost(), "Host is required");
    }


}
