package com.eharmony.services.swagger;

import com.eharmony.services.swagger.client.DocumentationRepositoryClient;
import com.eharmony.services.swagger.model.Documentation;
import io.swagger.jaxrs.config.BeanConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Objects;

public class DocumentationRepositoryPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentationRepositoryClient.class);
    private static final String SWAGGER_SPEC_PATH = "%s/swagger.json";
    private static final String SWAGGER_UI_PATH = "%s/swagger-ui";

    /**
     * Creates a documentation object and passes it to the documentation repository client to save it.
     */
    public void publish(final BeanConfig beanConfig, final String category, final String environment,
                        final String repositoryHost) {
        Objects.requireNonNull(beanConfig, "Bean config is required");
        Objects.requireNonNull(repositoryHost, "Repository host is required");
        String lowerEnvironment = environment != null ? environment.toLowerCase(Locale.ENGLISH) : null;

        // We don't care about local environments
        if (StringUtils.isEmpty(lowerEnvironment) || "local".equals(lowerEnvironment)) {
            LOG.debug("Ignoring documentation configuration for this host because the " +
                    "environment is not set or is local.");
            return;
        }

        try {
            LOG.info("Publishing Swagger documentation to {}", repositoryHost);
            final Documentation documentation = new Documentation();
            documentation.setServiceDescription(beanConfig.getDescription());
            documentation.setServiceName(beanConfig.getTitle());

            // If configured with annotations, description and title comes from info object
            if (StringUtils.isEmpty(documentation.getServiceDescription())) {
                documentation.setServiceDescription(beanConfig.getInfo().getDescription());
            }
            if (StringUtils.isEmpty(documentation.getServiceName())) {
                documentation.setServiceName(beanConfig.getInfo().getTitle());
            }

            documentation.setCategory(category);
            documentation.setSwaggerUiUrl(String.format(SWAGGER_UI_PATH, beanConfig.getBasePath()));
            documentation.setSwaggerSpecUrl(String.format(SWAGGER_SPEC_PATH, beanConfig.getBasePath()));
            documentation.setEnvironment(lowerEnvironment);
            documentation.setServiceHost(beanConfig.getHost());

            final DocumentationRepositoryClient client = createClient(repositoryHost);
            client.saveDocumentationForService(documentation);
        } catch (Exception ex) {
            LOG.error("Unable to update Swagger Documentation Repository", ex);
        }
    }

    /**
     * Creates a new DocumentationRepositoryClient using the repository host. Convenience method for testing.
     * @return A new DocumentationRepositoryClient
     */
    protected DocumentationRepositoryClient createClient(String repositoryHost) {
        return new DocumentationRepositoryClient(repositoryHost);
    }

}