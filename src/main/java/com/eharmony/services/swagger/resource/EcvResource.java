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

import com.eharmony.services.swagger.persistence.DocumentationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/ecv")
@Api(value = "ECV Server Status")
public class EcvResource {
    private static final Log LOG = LogFactory.getLog(EcvResource.class);
    private static final String STR_SERVER_UP = "SERVER UP";
    private static final String STR_SERVER_DOWN = "SERVER DOWN";

    @Autowired
    DocumentationRepository repository;

    /**
     * Checks documentation repository. If it returns anything, the service is up, if it throws an exception the service
     * is down
     * @return SERVER UP or SERVER DOWN
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Service status", notes = "Returns SERVER UP or SERVER DOWN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All ECV services are up", response = String.class),
            @ApiResponse(code = 503, message = "At least one ECV service is down", response = String.class) })
    public Response getServerStatus() {

        try {
            if (repository.getDocumentationForAllServices() != null) {
                return Response.ok().entity(STR_SERVER_UP).build();
            }
        } catch (Exception ex) {
            LOG.error("Unable to load documentation", ex);
        }
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(STR_SERVER_DOWN).build();
    }
}
