package com.eharmony.services.swagger;

import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@SwaggerDefinition(
        info = @Info(
                description = "A central web application for displaying and searching swagger UI's for " +
                        "different services.",
                version = "1.0",
                title = "EH Swagger Documentation Repository"
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTP},
        tags = {
                @Tag(name = "Shared", description = "Application shared by all of eHarmony Technology teams.")
        })
public interface SwaggerConfig {
}
