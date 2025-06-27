package com.copilotconnector.setup;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GraphService {
    
    private GraphServiceClient client;
    
    @Value("${azure.client-id}")
    private String clientId;
    
    @Value("${azure.client-secret}")
    private String clientSecret;
    
    @Value("${azure.tenant-id}")
    private String tenantId;

    public GraphServiceClient getClient() {
        if (client == null) {
            if (clientId == null || clientSecret == null || tenantId == null) {
                throw new IllegalStateException("Azure AD credentials not found in configuration");
            }
            
            // Create a ClientSecretCredential using the retrieved credentials
            ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .tenantId(tenantId)
                    .build();
            
            // Initialize the GraphServiceClient with the credential
            client = new GraphServiceClient(credential);
        }
        
        return client;
    }
}