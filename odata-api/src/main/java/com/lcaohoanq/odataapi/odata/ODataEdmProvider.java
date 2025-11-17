package com.lcaohoanq.odataapi.odata;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.*;
import org.apache.olingo.commons.api.ex.ODataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ODataEdmProvider extends CsdlAbstractEdmProvider {
    
    // Service Namespace
    public static final String NAMESPACE = "com.lcaohoanq.odata";
    
    // Entity Type Names
    public static final String ET_PRODUCT_NAME = "Product";
    public static final FullQualifiedName ET_PRODUCT_FQN = new FullQualifiedName(NAMESPACE, ET_PRODUCT_NAME);
    
    // Entity Set Names
    public static final String ES_PRODUCTS_NAME = "Products";
    
    // Container Name
    public static final String CONTAINER_NAME = "Container";
    public static final FullQualifiedName CONTAINER_FQN = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);
    
    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
        if (entityTypeName.equals(ET_PRODUCT_FQN)) {
            // Properties
            CsdlProperty id = new CsdlProperty()
                    .setName("Id")
                    .setType(EdmPrimitiveTypeKind.Int64.getFullQualifiedName())
                    .setNullable(false);
            
            CsdlProperty name = new CsdlProperty()
                    .setName("Name")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(100);
            
            CsdlProperty description = new CsdlProperty()
                    .setName("Description")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(500);
            
            CsdlProperty price = new CsdlProperty()
                    .setName("Price")
                    .setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            
            CsdlProperty quantity = new CsdlProperty()
                    .setName("Quantity")
                    .setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
            
            CsdlProperty category = new CsdlProperty()
                    .setName("Category")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(50);
            
            // Property Key
            CsdlPropertyRef propertyRef = new CsdlPropertyRef();
            propertyRef.setName("Id");
            
            // Configure EntityType
            CsdlEntityType entityType = new CsdlEntityType();
            entityType.setName(ET_PRODUCT_NAME);
            entityType.setProperties(Arrays.asList(id, name, description, price, quantity, category));
            entityType.setKey(Collections.singletonList(propertyRef));
            
            return entityType;
        }
        
        return null;
    }
    
    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
        if (entityContainer.equals(CONTAINER_FQN)) {
            if (entitySetName.equals(ES_PRODUCTS_NAME)) {
                CsdlEntitySet entitySet = new CsdlEntitySet();
                entitySet.setName(ES_PRODUCTS_NAME);
                entitySet.setType(ET_PRODUCT_FQN);
                return entitySet;
            }
        }
        
        return null;
    }
    
    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
        if (entityContainerName == null || entityContainerName.equals(CONTAINER_FQN)) {
            CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
            entityContainerInfo.setContainerName(CONTAINER_FQN);
            return entityContainerInfo;
        }
        
        return null;
    }
    
    @Override
    public List<CsdlSchema> getSchemas() throws ODataException {
        // Create Schema
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);
        
        // Add EntityTypes
        List<CsdlEntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ET_PRODUCT_FQN));
        schema.setEntityTypes(entityTypes);
        
        // Add EntityContainer
        schema.setEntityContainer(getEntityContainer());
        
        // Provide one Schema
        List<CsdlSchema> schemas = new ArrayList<>();
        schemas.add(schema);
        
        return schemas;
    }
    
    @Override
    public CsdlEntityContainer getEntityContainer() throws ODataException {
        // Create EntitySets
        List<CsdlEntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(CONTAINER_FQN, ES_PRODUCTS_NAME));
        
        // Create EntityContainer
        CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_NAME);
        entityContainer.setEntitySets(entitySets);
        
        return entityContainer;
    }
    
}
