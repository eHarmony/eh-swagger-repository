package com.eharmony.services.swagger.persistence;

import com.eharmony.services.swagger.model.Documentation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileDocumentationRepositoryTest {
    private static FileDocumentationRepository out = new FileDocumentationRepository();

    @BeforeClass
    public static void initMap() throws IOException {
        out.initializeDocumenationMap();
    }

    @Before
    public void setup() throws IOException {
        out.saveDocumentation(createTestDocumentation());
    }

    @Test
    public void getDocumentationByIdAndExpectReturned() {
        Documentation documentation = out.getDocumentationById("testname:test");
        assertTrue(documentation != null);
    }

    @Test
    public void removeDocumentationByIdAndExpectRemoved() throws IOException {
        String testId = "testname:test";
        assertTrue(out.getDocumentationById(testId) != null);
        out.removeDocumentationById(testId);
        assertTrue(out.getDocumentationById(testId) == null);
    }

    @Test
    public void saveDocumentationWhereDoesntExistAndExpectSaved() throws IOException {
        Documentation documentation = createTestDocumentation();
        documentation.setId(null);
        documentation.setServiceName("differentServiceName");
        documentation.setEnvironment("differentEnvironment");
        out.saveDocumentation(documentation);
        assertEquals((documentation.getServiceName() + ":" + documentation.getEnvironment()).toLowerCase(), documentation.getId());

        // Clean up for next tests
        out.removeDocumentationById(documentation.getId());
    }

    @Test
    public void saveDocumentationWhereExistAndExpectSaved() throws IOException {
        Documentation documentation = createTestDocumentation();
        documentation.setServiceDescription("This is an updated description");
        out.saveDocumentation(documentation);
        Documentation savedDocumentation = out.getDocumentationById(documentation.getId());
        assertEquals(documentation.getServiceDescription(), savedDocumentation.getServiceDescription());
    }

    @Test
    public void getDocumentationForAllServicesAndExpectReturned() {
        List<Documentation> documentation = out.getDocumentationForAllServices();
        assertTrue(documentation.size() == 1);
    }

    @Test
    public void getDocumentationByServiceNameAndEnvironmentAndExpectReturned() {
        String testId = "testname:test";
        Documentation documentation = out.getDocumentationByServiceNameAndEnvironment("testname", "test");
        assertEquals(testId, documentation.getId());
    }

    @AfterClass
    public static void cleanUp() {
        File file = new File("./documentation-repository.json");
        file.delete();
    }

    private Documentation createTestDocumentation() {
        Documentation documentation = new Documentation();
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
