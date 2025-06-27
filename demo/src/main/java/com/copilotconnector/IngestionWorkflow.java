package com.example.graphconnector.workflow;

import com.example.graphconnector.config.ConnectionConfiguration;
import com.example.graphconnector.service.GraphService;
import com.microsoft.graph.models.externalconnectors.ExternalItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IngestionWorkflow {
    
    @Autowired
    private GraphService graphService;
    
    // Static lists to hold content and transformed items
    private static List<Map<String, Object>> content = new ArrayList<>();
    private static List<ExternalItem> items = new ArrayList<>();
    
    public void extract() {
        // Populate the content list with data from the External Service
        // This is where you would implement your data extraction logic
    }
    
    public ExternalItem transform(Map<String, Object> item) {
        // Create a new ExternalItem object and populate its properties
        ExternalItem externalItem = new ExternalItem();
        
        // This is an example layout of how you can transform the item
        // The fields being set have to match 1:1 with the schema defined in SchemaExample
        // externalItem.id = (String) item.get("Id");
        // externalItem.additionalData = new HashMap<>();
        // externalItem.additionalData.put("Url", item.get("Url"));
        // externalItem.additionalData.put("IconUrl", item.get("IconUrl"));
        // externalItem.additionalData.put("Title", item.get("Title"));
        // externalItem.additionalData.put("Company", item.get("Company"));
        // externalItem.additionalData.put("Form", item.get("Form"));
        // externalItem.additionalData.put("DateFiled", item.get("DateFiled"));
        
        // Add the transformed item to the items list
        items.add(externalItem);
        return externalItem;
    }
    
    public void load() throws Exception {
        // Iterate over each item in the items list
        for (ExternalItem item : items) {
            System.out.print(String.format("Loading item %s...", item.id));
            try {
                // Put the item into the GraphService client
                String connectionId = URLEncoder.encode(
                    ConnectionConfiguration.getExternalConnection().id, 
                    StandardCharsets.UTF_8
                );
                
                graphService.getClient().external().connections()
                        .byExternalConnectionId(connectionId)
                        .items()
                        .byExternalItemId(item.id)
                        .put(item);
                
                System.out.println("DONE");
                
                // Get the URL from the item's additionalData map
                if (item.additionalData != null && item.additionalData.containsKey("Url")) {
                    String url = (String) item.additionalData.get("Url");
                    // Do something with the URL if needed
                }
                
            } catch (Exception ex) {
                // Output an error message if an exception occurs
                System.out.println("ERROR");
                System.out.println("Error loading item: " + ex.getMessage());
            }
        }
    }
    
    public void loadContent() throws Exception {
        // Call the Extract method to populate the content list
        extract();
        
        // Iterate over each item in the content list and transform it
        for (Map<String, Object> item : content) {
            transform(item);
        }
        
        // Call the Load method to load the transformed items
        load();
    }
}