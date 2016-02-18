package com.eharmony.services.swagger.resource;

import com.eharmony.services.swagger.model.Documentation;
import com.eharmony.services.swagger.persistence.DocumentationRepository;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class DocumentationRepositoryResourceTest {

    @Test
    public void getAllDocumentationAndExpectAllReturnedWith200() {
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        Mockito.when(mockRepository.getDocumentationForAllServices()).thenReturn(Arrays.asList(new Documentation()));
        out.repository = mockRepository;

        Response response = out.getAllDocumentation();
        List<Documentation> documentation = (List<Documentation>)response.getEntity();
        assertEquals(1, documentation.size());
        assertEquals(200, response.getStatus());
    }

    @Test
    public void getAllDocumentationWithErrorAndExpect500() {
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        Mockito.when(mockRepository.getDocumentationForAllServices()).thenThrow(new RuntimeException("Test Exception"));
        out.repository = mockRepository;

        Response response = out.getAllDocumentation();
        assertEquals(500, response.getStatus());
    }

    @Test
    public void saveDocumentationWithAllFieldsAndExpectCreatedWith201() throws Exception {
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        out.repository = mockRepository;

        Documentation documentation = createTestDocumentation();

        Response response = out.saveDocumentationForService(documentation);
        assertEquals(201, response.getStatus());

        Mockito.verify(mockRepository, Mockito.times(1)).saveDocumentation(Mockito.any(Documentation.class));
    }

    @Test
    public void saveDocumentationWhereExistsAndExpectUpdatedWith200() throws Exception {
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        out.repository = mockRepository;

        Documentation documentation = createTestDocumentation();

        Mockito.when(mockRepository.getDocumentationByServiceNameAndEnvironment(
                Mockito.eq(documentation.getServiceName()),
                Mockito.eq(documentation.getEnvironment()))).thenReturn(documentation);

        Response response = out.saveDocumentationForService(documentation);
        assertEquals(200, response.getStatus());

        Mockito.verify(mockRepository, Mockito.times(1)).saveDocumentation(Mockito.any(Documentation.class));
    }

    @Test
    public void saveDocumentationWithMissingFieldsAndExpect400() throws Exception {
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        out.repository = mockRepository;

        Documentation documentation = createTestDocumentation();

        documentation.setCategory(null);
        Response response = out.saveDocumentationForService(documentation);
        assertEquals(400, response.getStatus());

        documentation.setCategory("test");
        documentation.setEnvironment(null);
        response = out.saveDocumentationForService(documentation);
        assertEquals(400, response.getStatus());

        documentation.setEnvironment("test");
        documentation.setServiceDescription(null);
        response = out.saveDocumentationForService(documentation);
        assertEquals(400, response.getStatus());

        documentation.setServiceDescription("testdescription");
        documentation.setServiceName(null);
        response = out.saveDocumentationForService(documentation);
        assertEquals(400, response.getStatus());

        documentation.setServiceName("testname");
        documentation.setSwaggerSpecUrl(null);
        response = out.saveDocumentationForService(documentation);
        assertEquals(400, response.getStatus());

        documentation.setSwaggerSpecUrl("/spec-url");
        documentation.setServiceHost(null);
        response = out.saveDocumentationForService(documentation);
        assertEquals(400, response.getStatus());

        documentation.setServiceHost("testhost");
        documentation.setSwaggerUiUrl(null);
        response = out.saveDocumentationForService(documentation);
        assertEquals(400, response.getStatus());

        Mockito.verify(mockRepository, Mockito.never()).saveDocumentation(Mockito.any(Documentation.class));
    }

    @Test
    public void saveDocumentationWithErrorAndExpect500() throws Exception {
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        Mockito.doThrow(new RuntimeException("Test Exception")).when(mockRepository)
                .saveDocumentation(Mockito.any(Documentation.class));
        out.repository = mockRepository;

        Response response = out.saveDocumentationForService(createTestDocumentation());
        assertEquals(500, response.getStatus());
    }

    @Test
    public void getDocumentationByIdAndExpectReturnedWith200() {
        String testId = "testid";
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        Mockito.when(mockRepository.getDocumentationById(Mockito.eq(testId)))
                .thenReturn(createTestDocumentation());
        out.repository = mockRepository;

        Response response = out.getDocumentationById(testId);
        assertEquals(200, response.getStatus());
        assertTrue(response.getEntity() instanceof Documentation);
    }

    @Test
    public void getDocumentationByIdWhereNotFoundAndExpect404() {
        String testId = "testid";
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        out.repository = mockRepository;

        Response response = out.getDocumentationById(testId);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void getDocumentationByIdWhereErrorAndExpect500() {
        String testId = "testid";
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        Mockito.when(mockRepository.getDocumentationById(Mockito.eq(testId))).thenThrow(
                new RuntimeException("Test Exception"));
        out.repository = mockRepository;

        Response response = out.getDocumentationById(testId);
        assertEquals(500, response.getStatus());
    }

    @Test
    public void removeDocumentationByIdAndExpect200() throws Exception {
        String testId = "testid";
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        Mockito.when(mockRepository.getDocumentationById(Mockito.eq(testId))).thenReturn(createTestDocumentation());
        out.repository = mockRepository;

        Response response = out.removeDocumentationForService(testId);
        assertEquals(200, response.getStatus());
        Mockito.verify(mockRepository, Mockito.times(1)).removeDocumentationById(testId);
    }

    @Test
    public void removeDocumentationByIdWhereErrorAndExpect500() throws Exception {
        String testId = "testid";
        DocumentationRepositoryResource out = new DocumentationRepositoryResource();

        DocumentationRepository mockRepository =  Mockito.mock(DocumentationRepository.class);
        Mockito.when(mockRepository.getDocumentationById(Mockito.eq(testId)))
                .thenThrow(new RuntimeException("Test Exception"));
        out.repository = mockRepository;

        Response response = out.removeDocumentationForService(testId);
        assertEquals(500, response.getStatus());
        Mockito.verify(mockRepository, Mockito.never()).removeDocumentationById(testId);
    }

    private Documentation createTestDocumentation() {
        Documentation documentation = new Documentation();
        documentation.setId("testid");
        documentation.setServiceHost("testhost");
        documentation.setServiceName("testname");
        documentation.setSwaggerSpecUrl("/spec-url");
        documentation.setSwaggerUiUrl("/ui-url");
        documentation.setCategory("test");
        documentation.setEnvironment("test");
        documentation.setServiceDescription("testdescription");

        return documentation;
    }
}
