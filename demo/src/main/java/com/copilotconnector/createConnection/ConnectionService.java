package com.copilotconnector.createConnection;

import com.example.graphconnector.config.ConnectionConfiguration;
import com.example.graphconnector.transform.SchemaExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    
    @Autowired
    private GraphService graphService;
    
    @Autowired
    private LoggingService loggingService;

    public void createConnection() throws Exception {
        System.out.print("Creating connection...");
        
        // Post a new connection to the GraphService client
        graphService.getClient().external().connections()
                .post(ConnectionConfiguration.getExternalConnection());
        
        System.out.println("DONE");
    }

    public void createSchema() throws Exception {
        System.out.println("Creating schema...");
        
        // Patch the schema for the specified connection
        String connectionId = ConnectionConfiguration.getExternalConnection().id;
        graphService.getClient().external().connections()
                .byExternalConnectionId(connectionId)
                .schema()
                .patch(SchemaExample.getSchema());
        
        System.out.println("DONE");
    }

    public void provisionConnection() {
        try {
            // Attempt to create a connection
            createConnection();
            // Attempt to create a schema
            createSchema();
        } catch (Exception ex) {
            // Log any exceptions that occur
            loggingService.logError("Error provisioning connection: {}", ex.getMessage());
            throw new RuntimeException("Failed to provision connection", ex);
        }
    }
}