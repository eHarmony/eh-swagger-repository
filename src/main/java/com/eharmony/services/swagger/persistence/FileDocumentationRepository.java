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

package com.eharmony.services.swagger.persistence;

import com.eharmony.services.swagger.model.Documentation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple file based persistence implementation. Meant to be used when running a single instance of the repository.
 * This implementation uses a map in order to prevent a service from being added multiple times.
 */
public class FileDocumentationRepository implements DocumentationRepository {
    private static final String DEFAULT_FILE_NAME = "/documentation-repository.json";
    private static final String DEFAULT_FILE_LOCATION = ".";
    private final ObjectMapper mapper = new ObjectMapper();
    private ConcurrentHashMap<String, Documentation> documentationMap;
    private String fileLocation;

    @Override
    public Documentation getDocumentationById(String id) {
        return documentationMap.get(id);
    }

    @Override
    public void removeDocumentationById(String id) throws IOException {
        documentationMap.remove(id);
        writeDocumentationToFile(documentationMap);
    }

    @Override
    public void saveDocumentation(Documentation documentation) throws IOException {
        if (StringUtils.isEmpty(documentation.getId())) {
            documentation.setId(createId(documentation.getServiceName(), documentation.getEnvironment()));
        }
        documentationMap.put(documentation.getId(), documentation);
        writeDocumentationToFile(documentationMap);
    }

    @Override
    public List<Documentation> getDocumentationForAllServices() {
        List<Documentation> documentation = new ArrayList<>(documentationMap.values());

        Collections.sort(documentation, new DocumentationSort());
        return documentation;
    }

    @Override
    public Documentation getDocumentationByServiceNameAndEnvironment(String serviceName, String environment) {
        return documentationMap.get(createId(serviceName, environment));
    }

    /**
     * Initializes documentation map by reading json file from file system.
     * @throws IOException Unable to load the file.
     */
    @PostConstruct
    public void initializeDocumenationMap() throws IOException {
        if (StringUtils.isEmpty(this.fileLocation)) {
            this.fileLocation = DEFAULT_FILE_LOCATION;
        }
        documentationMap = new ConcurrentHashMap<>(readDocumentationFromFile());
    }

    /**
     * Writes documentation to a file in JSON format.
     * @param documentationMap Map of id to documentation.
     * @throws IOException Unable to load the file.
     */
    private synchronized void writeDocumentationToFile(Map<String, Documentation> documentationMap) throws IOException {
        File repositoryFile = new File(this.fileLocation, DEFAULT_FILE_NAME);
        if (repositoryFile.exists() || repositoryFile.createNewFile()) {
            mapper.writeValue(repositoryFile, documentationMap);
        }
    }

    /**
     * Reads JSON documentation from file and converts it to a map of documentation.
     * @return Map of id to documentation.
     * @throws IOException Unable to load the file.
     */
    private synchronized Map<String, Documentation> readDocumentationFromFile() throws IOException {
        File repositoryFile = new File(this.fileLocation, DEFAULT_FILE_NAME);
        if (!repositoryFile.exists()) {
            return new HashMap<>();
        }
        return mapper.readValue(repositoryFile, new DocumentationMapReference());
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    private String createId(String serviceName, String environment) {
        return (serviceName + ":" + environment).replaceAll("\\s+", "").toLowerCase(Locale.ENGLISH);
    }

    private static class DocumentationSort implements Comparator<Documentation>, Serializable {
        private static final long serialVersionUID = -4747949930562871645L;

        @Override
        public int compare(Documentation docSource, Documentation docTarget) {
            int compare = docSource.getServiceName().toLowerCase(Locale.ENGLISH)
                    .compareTo(docTarget.getServiceName().toLowerCase(Locale.ENGLISH));
            if (compare == 0) {
                compare = docSource.getEnvironment().toLowerCase(Locale.ENGLISH)
                        .compareTo(docTarget.getEnvironment().toLowerCase(Locale.ENGLISH));
            }
            return compare;
        }
    }

    private static class DocumentationMapReference extends TypeReference<Map<String, Documentation>> {

    }
}
