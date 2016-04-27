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

import java.util.List;

/**
 * Interface for defining the operations the documentation respository has.
 */
public interface DocumentationRepository {
    Documentation getDocumentationById(String id);

    void removeDocumentationById(String id) throws Exception;

    void saveDocumentation(Documentation documentation) throws Exception;

    List<Documentation> getDocumentationForAllServices();

    Documentation getDocumentationByServiceNameAndEnvironment(String serviceName, String environment);
}
