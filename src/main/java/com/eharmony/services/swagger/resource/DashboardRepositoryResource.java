package com.eharmony.services.swagger.resource;

import com.eharmony.services.swagger.model.Dashboard;
import com.eharmony.services.swagger.persistence.DashboardRepository;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/1.0/dashboards")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Api(value = "Dashboard Repository Resource")
public class DashboardRepositoryResource {
    private static final Log LOG = LogFactory.getLog(DashboardRepositoryResource.class);

    @Autowired
    DashboardRepository repository;

    /**
     * Returns a list of dashboards, sorted by dashboard name.
     */
    @GET
    @ApiOperation(value = "Get All Dashboards",
            notes = "Returns a list of dashboards, sorted by dashboard name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The list of all dashboards sorted by dashboard name",
                    response = Dashboard.class,
                    responseContainer = "List"),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the list of dashboards")
            })
    public Response getAllDashboards() {
        try {
            List<Dashboard> dashboards = repository.getAllDashboards();
            return Response.ok().entity(dashboards).build();
        } catch (Exception ex) {
            LOG.error("Unable to retrieve dashboards", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Saves a services dashboard. Validates all required fields. If the same service name and
     * environment is found, it is overwritten.
     *
     * @param dashboard The unique dashboard id.
     */
    @PUT
    @Path(value = "/{id}")
    @ApiOperation(value = "Save dashboard",
            notes = "Saves a services dashboard. Validates all required fields. If the same service name and " +
                    "environment is found, it is overwritten.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The dashboard was overwritten successfully."),
            @ApiResponse(code = 201, message = "The dashboard was created successfully."),
            @ApiResponse(code = 400, message = "A required field is missing."),
            @ApiResponse(code = 500, message = "An error occurred when attempting to save the dashboard.")
            })
    public Response saveDashboard(
            @PathParam(value = "id") @ApiParam(value = "The unique dashboard id.") String dashboardId,
            @ApiParam(value = "A dashboard object") Dashboard dashboard) {
        try {
            validateDashboard(dashboard);
            repository.saveDashboard(dashboard);
            return Response.ok(dashboard).build();
        } catch (IllegalArgumentException iae) {
            return Response.status(400).entity(iae.getMessage()).build();
        } catch (Exception ex) {
            LOG.error("Unable to save dashboard", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Retrieves a services dashboard by id.
     *
     * @param dashboardId The unique dashboard id.
     */
    @GET
    @Path(value = "/{id}")
    @ApiOperation(value = "Find dashboard by id",
            notes = "Retrieves a services dashboard by id.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The dashboard was found for the given id.",
                    response = Dashboard.class),
            @ApiResponse(code = 404, message = "The dashboard was not found for that id."),
            @ApiResponse(code = 500, message = "An error occurred when attempting to retrieve the dashboard.")
            })
    public Response getDashboardById(
            @PathParam(value = "id") @ApiParam(value = "The unique dashboard id.") String dashboardId) {
        try {
            Dashboard dashboard = repository.getDashboardById(dashboardId);
            if (dashboard == null) {
                return Response.status(404).build();
            }
            return Response.ok().entity(dashboard).build();
        } catch (Exception ex) {
            LOG.error("Unable to retrieve dashboard", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Remove dashboard by id.
     *
     * @param dashboardId The unique dashboard id.
     */
    @DELETE
    @Path(value = "/{id}")
    @ApiOperation(value = "Remove dashboard",
            notes = "Removes a services dashboard by id.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The dashboard was removed successfully."),
            @ApiResponse(code = 404, message = "The dashboard was not found for that id."),
            @ApiResponse(code = 500, message = "An error occurred when attempting to remove the dashboard.")
            })
    public Response removeDashboardById(
            @PathParam(value = "id") @ApiParam(value = "The unique dashboard id.") String dashboardId) {
        try {
            if (repository.getDashboardById(dashboardId) == null) {
                return Response.status(404).build();
            }
            repository.removeDashboardById(dashboardId);
            return Response.ok().build();
        } catch (Exception ex) {
            LOG.error("Unable to remove dashboard", ex);
            return Response.serverError().build();
        }
    }

    /**
     * Validates required fields on the dashboard instance.
     *
     * @param dashboard dashboard to validate
     */
    private void validateDashboard(Dashboard dashboard) {
        Validate.notEmpty(dashboard.getName(), "Name is required");
        Validate.notEmpty(dashboard.getId(), "ID is required");
        Validate.notEmpty(dashboard.getDescription(), "Description is required");
        Validate.notEmpty(dashboard.getDocumentationIds(), "At least one documentation ID is required");
    }


}
