package com.example.graphconnector.controller;

import com.example.graphconnector.service.ConnectionService;
import com.example.graphconnector.service.LoggingService;
import com.example.graphconnector.service.BackgroundTaskQueue;
import com.example.graphconnector.workflow.IngestionWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@RestController
public class GraphConnectorController {

    @Autowired
    private LoggingService loggingService;
    
    @Autowired
    private ConnectionService connectionService;
    
    @Autowired
    private BackgroundTaskQueue backgroundTaskQueue;
    
    @Autowired
    private IngestionWorkflow ingestionWorkflow;
    
    @Value("${azure.client-id:your-client-id}")
    private String clientId;

    @PostMapping("/grantPermissions")
    public void grantPermissions(@RequestBody String tenantId, HttpServletResponse response) throws IOException {
        // Log the tenant ID
        loggingService.logInformation("Received tenant ID: {}", tenantId);
        
        // Store the tenant ID in a text file
        Files.write(Paths.get("tenantid.txt"), tenantId.getBytes(), 
                   StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        
        // Redirect the user to the specified URL
        String redirectUrl = String.format(
            "https://login.microsoftonline.com/organizations/adminconsent?client_id=%s", 
            clientId
        );
        
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/provisionconnection")
    public ResponseEntity<String> provisionConnection(@RequestBody String tenantId) {
        try {
            // Log the tenant ID
            loggingService.logInformation("Provisioning connection for tenant ID: {}", tenantId);
            
            // Call the ProvisionConnection method with the tenant ID
            connectionService.provisionConnection();
            
            return ResponseEntity.ok("{\"status\": \"Connection provisioned successfully\"}");
        } catch (Exception e) {
            loggingService.logError("Error provisioning connection: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to provision connection\"}");
        }
    }

    @PostMapping("/loadcontent")
    public ResponseEntity<String> loadContent(@RequestBody String tenantId) {
        try {
            // Log the tenant ID
            loggingService.logInformation("Loading content for tenant ID: {}", tenantId);
            
            // Queue the long-running task
            backgroundTaskQueue.queueBackgroundWorkItem(() -> {
                try {
                    ingestionWorkflow.loadContent();
                } catch (Exception e) {
                    loggingService.logError("Error in background task: {}", e.getMessage());
                }
            });
            
            // Return a response immediately
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("{\"status\": \"Content loading queued\"}");
        } catch (Exception e) {
            loggingService.logError("Error queuing content load: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to queue content loading\"}");
        }
    }
}