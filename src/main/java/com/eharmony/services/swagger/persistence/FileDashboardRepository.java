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

import com.eharmony.services.swagger.model.Dashboard;
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

public class FileDashboardRepository implements DashboardRepository {
    private static final String DEFAULT_FILE_NAME = "/dashboard-repository.json";
    private static final String DEFAULT_FILE_LOCATION = ".";
    private final ObjectMapper mapper = new ObjectMapper();
    private ConcurrentHashMap<String, Dashboard> dashboardMap;
    private String fileLocation;

    @Override
    public Dashboard getDashboardById(String id) {
        return dashboardMap.get(id);
    }

    @Override
    public void removeDashboardById(String id) throws IOException {
        dashboardMap.remove(id);
        writeDashboardToFile(dashboardMap);
    }

    @Override
    public void saveDashboard(Dashboard dashboard) throws IOException {
        dashboardMap.put(dashboard.getId(), dashboard);
        writeDashboardToFile(dashboardMap);
    }

    @Override
    public List<Dashboard> getAllDashboards() {
        List<Dashboard> dashboards = new ArrayList<>(dashboardMap.values());

        Collections.sort(dashboards, new DashboardSort());
        return dashboards;
    }

    /**
     * Initializes dashboard map by reading json file from file system.
     * @throws IOException Unable to load the file.
     */
    @PostConstruct
    public void initializeDashboardnMap() throws IOException {
        if (StringUtils.isEmpty(this.fileLocation)) {
            this.fileLocation = DEFAULT_FILE_LOCATION;
        }
        dashboardMap = new ConcurrentHashMap<>(readDashboardFromFile());
    }

    /**
     * Writes dashboard to a file in JSON format.
     * @param dashboardMap Map of id to dashboard.
     * @throws IOException Unable to load the file.
     */
    private synchronized void writeDashboardToFile(Map<String, Dashboard> dashboardMap) throws IOException {
        File repositoryFile = new File(this.fileLocation, DEFAULT_FILE_NAME);
        if (repositoryFile.exists() || repositoryFile.createNewFile()) {
            mapper.writeValue(repositoryFile, dashboardMap);
        }
    }

    /**
     * Reads JSON dashboard from file and converts it to a map of dashboard.
     * @return Map of id to dashboard.
     * @throws IOException Unable to load the file.
     */
    private synchronized Map<String, Dashboard> readDashboardFromFile() throws IOException {
        File repositoryFile = new File(this.fileLocation, DEFAULT_FILE_NAME);
        if (!repositoryFile.exists()) {
            return new HashMap<>();
        }
        return mapper.readValue(repositoryFile, new DashboardMapReference());
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    private static class DashboardSort implements Comparator<Dashboard>, Serializable {
        private static final long serialVersionUID = -4747949930562871645L;

        @Override
        public int compare(Dashboard dashSource, Dashboard dashTarget) {
            return dashSource.getName().toLowerCase(Locale.ENGLISH)
                    .compareTo(dashTarget.getName().toLowerCase(Locale.ENGLISH));
        }
    }

    private static class DashboardMapReference extends TypeReference<Map<String, Dashboard>> {

    }
}
