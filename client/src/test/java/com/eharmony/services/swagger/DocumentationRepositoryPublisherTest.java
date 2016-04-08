package com.eharmony.services.swagger;

import com.eharmony.services.swagger.client.DocumentationRepositoryClient;
import com.eharmony.services.swagger.model.Documentation;
import io.swagger.jaxrs.config.BeanConfig;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DocumentationRepositoryPublisherTest {
    private static final String HOST = "http://somehost.com";

    @Test
    public void initializeDocumentationAndExpectSavedAndAllFieldsPopulated() throws IOException {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setBasePath("/api");
        beanConfig.setDescription("Test Description");
        beanConfig.setHost("testhost.com:1234");
        beanConfig.setTitle("Test Service");

        DocumentationRepositoryPublisher out = Mockito.spy(new DocumentationRepositoryPublisher());

        DocumentationRepositoryClient mockClient = Mockito.mock(DocumentationRepositoryClient.class);
        Mockito.doReturn(mockClient).when(out).createClient(HOST);

        out.publish(beanConfig,
                "Test",
                "NP",
                HOST);

        ArgumentCaptor<Documentation> argumentCaptor = ArgumentCaptor.forClass(Documentation.class);
        Mockito.verify(mockClient, Mockito.times(1)).saveDocumentationForService(argumentCaptor.capture());

        Documentation savedDocumentation = argumentCaptor.getValue();
        assertEquals(beanConfig.getTitle(), savedDocumentation.getServiceName());
        assertEquals(beanConfig.getDescription(), savedDocumentation.getServiceDescription());
        assertEquals(beanConfig.getHost(), savedDocumentation.getServiceHost());
        assertEquals(beanConfig.getBasePath() + "/swagger-ui", savedDocumentation.getSwaggerUiUrl());
        assertEquals(beanConfig.getBasePath() + "/swagger.json", savedDocumentation.getSwaggerSpecUrl());
        assertEquals("Test", savedDocumentation.getCategory());
        assertEquals("np", savedDocumentation.getEnvironment());
    }

    @Test
    public void postDocumentationWithExceptionAndExpectExceptionSwallowed() throws IOException {
        BeanConfig beanConfig = new BeanConfig();

        DocumentationRepositoryPublisher out = Mockito.spy(
                new DocumentationRepositoryPublisher());

        DocumentationRepositoryClient mockClient = Mockito.mock(DocumentationRepositoryClient.class);
        Mockito.doThrow(new IOException()).when(mockClient)
                .saveDocumentationForService(Mockito.any(Documentation.class));

        Mockito.doReturn(mockClient).when(out).createClient(HOST);

        out.publish(beanConfig,
                "Test",
                "NP",
                HOST);
    }

    @Test
    public void postDocumentationWithLocalEnvironmentAndExpectNothingHappens() throws IOException {
        BeanConfig beanConfig = new BeanConfig();

        DocumentationRepositoryPublisher out = Mockito.spy(
                new DocumentationRepositoryPublisher());

        DocumentationRepositoryClient mockClient = Mockito.mock(DocumentationRepositoryClient.class);
        Mockito.doThrow(new IOException()).when(mockClient)
                .saveDocumentationForService(Mockito.any(Documentation.class));

        Mockito.doReturn(mockClient).when(out).createClient(HOST);

        out.publish(beanConfig,
                "Test",
                "LOCAL",
                HOST);

        Mockito.verify(mockClient, Mockito.never()).saveDocumentationForService(Mockito.any(Documentation.class));
    }
}
