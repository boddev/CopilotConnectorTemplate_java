package com.example.graphconnector.config;

import com.microsoft.graph.models.externalconnectors.ExternalConnection;
import com.microsoft.graph.models.externalconnectors.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ConnectionConfiguration {
    
    public static ExternalConnection getExternalConnection() {
        ExternalConnection connection = new ExternalConnection();
        connection.id = "your-connection-id"; // Replace with your connection ID
        connection.name = "Your Connection Name";
        connection.description = "Your connection description";
        connection.configuration = new Configuration();
        // Add configuration properties as needed
        
        return connection;
    }
}