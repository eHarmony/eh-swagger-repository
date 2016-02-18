package com.eharmony.services.swagger.client;


import com.eharmony.services.swagger.model.Documentation;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;

public class DocumentationRepositoryClient {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private static final String DOCUMENTATION_API = "%s/api/1.0/documentation";
    private final String documentationHost;
    private final ObjectMapper objectMapper;

    /**
     * Initializes the client with the repository base url.
     * @param documentationHost the base hostname or url to the central swagger repository.
     */
    public DocumentationRepositoryClient(String documentationHost) {
        String host = Objects.requireNonNull(documentationHost);
        if (!host.startsWith("http")) {
            host = "http://" + host;
        }
        this.documentationHost = host;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Saves a services documentation. Validates all required fields. If the same service name and environment is
     * found, it is overwritten. Written using HttpURLConnection instead of Jersey client to avoid Jersey version
     * issues.
     *
     * @param documentation A single service documentation object
     */
    public void saveDocumentationForService(Documentation documentation) throws IOException {
        doPost(objectMapper.writeValueAsString(documentation));
    }

    protected void doPost(String documentationInput) throws IOException {
        URL url = new URL(String.format(DOCUMENTATION_API, documentationHost));
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(documentationInput.getBytes(Charset.defaultCharset()));
            os.flush();

            if (conn.getResponseCode() == 400) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    StringBuilder response = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        response.append(output);
                    }
                    throw new IllegalArgumentException(response.toString());
                }
            } else if (conn.getResponseCode() == 500) {
                throw new RuntimeException("Failed to update documentation repository.");
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

    }

}
