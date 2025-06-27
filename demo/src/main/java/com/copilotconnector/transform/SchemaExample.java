package com.copilotconnector.transform;

import com.microsoft.graph.models.externalconnectors.Schema;
import com.microsoft.graph.models.externalconnectors.Property;
import com.microsoft.graph.models.externalconnectors.PropertyType;
import com.microsoft.graph.models.externalconnectors.Label;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.ArrayList;

@Component
public class SchemaExample {
    
    public static Schema getSchema() {
        // Return a new Schema object with predefined properties
        // Labels Title, Url and IconUrl are required for Copilot usage
        Schema schema = new Schema();
        schema.baseType = "microsoft.graph.externalItem";
        
        schema.properties = new ArrayList<>();
        
        // Title property
        Property titleProperty = new Property();
        titleProperty.name = "Title";
        titleProperty.type = PropertyType.String;
        titleProperty.isQueryable = true;
        titleProperty.isSearchable = true;
        titleProperty.isRetrievable = true;
        titleProperty.labels = Arrays.asList(Label.Title);
        schema.properties.add(titleProperty);
        
        // Company property
        Property companyProperty = new Property();
        companyProperty.name = "Company";
        companyProperty.type = PropertyType.String;
        companyProperty.isRetrievable = true;
        companyProperty.isSearchable = true;
        companyProperty.isQueryable = true;
        schema.properties.add(companyProperty);
        
        // Url property
        Property urlProperty = new Property();
        urlProperty.name = "Url";
        urlProperty.type = PropertyType.String;
        urlProperty.isRetrievable = true;
        urlProperty.labels = Arrays.asList(Label.Url);
        schema.properties.add(urlProperty);
        
        // IconUrl property
        Property iconUrlProperty = new Property();
        iconUrlProperty.name = "IconUrl";
        iconUrlProperty.type = PropertyType.String;
        iconUrlProperty.isRetrievable = true;
        iconUrlProperty.labels = Arrays.asList(Label.IconUrl);
        schema.properties.add(iconUrlProperty);
        
        // Form property
        Property formProperty = new Property();
        formProperty.name = "Form";
        formProperty.type = PropertyType.String;
        formProperty.isRetrievable = true;
        formProperty.isSearchable = true;
        formProperty.isQueryable = true;
        schema.properties.add(formProperty);
        
        // DateFiled property
        Property dateFiledProperty = new Property();
        dateFiledProperty.name = "DateFiled";
        dateFiledProperty.type = PropertyType.DateTime;
        dateFiledProperty.isRetrievable = true;
        dateFiledProperty.isSearchable = true;
        dateFiledProperty.isQueryable = true;
        dateFiledProperty.labels = Arrays.asList(Label.CreatedDateTime);
        schema.properties.add(dateFiledProperty);
        
        return schema;
    }
}