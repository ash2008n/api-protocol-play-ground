package com.lcaohoanq.odataapi.odata;

import com.lcaohoanq.odataapi.model.Product;
import com.lcaohoanq.odataapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.olingo.commons.api.data.*;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.processor.EntityProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.EntitySerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ODataEntityProcessor implements EntityCollectionProcessor, EntityProcessor {
    
    private final ProductService productService;
    private OData odata;
    private ServiceMetadata serviceMetadata;
    
    @Override
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }
    
    @Override
    public void readEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo, 
                                     org.apache.olingo.commons.api.format.ContentType responseFormat) 
            throws ODataApplicationException, ODataLibraryException {
        
        // Retrieve the requested EntitySet
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
        EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
        
        // Fetch data from database
        EntityCollection entityCollection = getProducts();
        
        // Serialize
        ODataSerializer serializer = odata.createSerializer(responseFormat);
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();
        String selectList = odata.createUriHelper().buildContextURLSelectList(
                edmEntityType, null, null);
        
        ContextURL contextUrl = ContextURL.with()
                .entitySet(edmEntitySet)
                .selectList(selectList)
                .build();
        
        EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
                .contextURL(contextUrl)
                .build();
        
        SerializerResult serializerResult = serializer.entityCollection(
                serviceMetadata, edmEntityType, entityCollection, opts);
        
        InputStream serializedContent = serializerResult.getContent();
        
        // Configure response
        response.setContent(serializedContent);
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader("Content-Type", responseFormat.toContentTypeString());
    }
    
    @Override
    public void readEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, 
                          org.apache.olingo.commons.api.format.ContentType responseFormat) 
            throws ODataApplicationException, ODataLibraryException {
        
        // Retrieve the requested EntitySet
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
        EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
        
        // Get the key
        List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
        Entity entity = getProduct(keyPredicates);
        
        if (entity == null) {
            throw new ODataApplicationException("Entity not found", 
                    HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
        
        // Serialize
        ODataSerializer serializer = odata.createSerializer(responseFormat);
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();
        
        ContextURL contextUrl = ContextURL.with()
                .entitySet(edmEntitySet)
                .build();
        
        EntitySerializerOptions options = EntitySerializerOptions.with()
                .contextURL(contextUrl)
                .build();
        
        SerializerResult serializerResult = serializer.entity(
                serviceMetadata, edmEntityType, entity, options);
        
        InputStream serializedContent = serializerResult.getContent();
        
        // Configure response
        response.setContent(serializedContent);
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader("Content-Type", responseFormat.toContentTypeString());
    }
    
    @Override
    public void createEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, 
                            org.apache.olingo.commons.api.format.ContentType requestFormat, 
                            org.apache.olingo.commons.api.format.ContentType responseFormat) 
            throws ODataApplicationException, ODataLibraryException {
        
        // Not implemented for this demo
        throw new ODataApplicationException("Create not implemented", 
                HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ENGLISH);
    }
    
    @Override
    public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, 
                            org.apache.olingo.commons.api.format.ContentType requestFormat, 
                            org.apache.olingo.commons.api.format.ContentType responseFormat) 
            throws ODataApplicationException, ODataLibraryException {
        
        // Not implemented for this demo
        throw new ODataApplicationException("Update not implemented", 
                HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ENGLISH);
    }
    
    @Override
    public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo) 
            throws ODataApplicationException, ODataLibraryException {
        
        // Not implemented for this demo
        throw new ODataApplicationException("Delete not implemented", 
                HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ENGLISH);
    }
    
    private EntityCollection getProducts() {
        EntityCollection entityCollection = new EntityCollection();
        List<Product> products = productService.getAllProducts();
        
        for (Product product : products) {
            Entity entity = new Entity();
            entity.addProperty(new Property(null, "Id", ValueType.PRIMITIVE, product.getId()));
            entity.addProperty(new Property(null, "Name", ValueType.PRIMITIVE, product.getName()));
            entity.addProperty(new Property(null, "Description", ValueType.PRIMITIVE, product.getDescription()));
            entity.addProperty(new Property(null, "Price", ValueType.PRIMITIVE, product.getPrice()));
            entity.addProperty(new Property(null, "Quantity", ValueType.PRIMITIVE, product.getQuantity()));
            entity.addProperty(new Property(null, "Category", ValueType.PRIMITIVE, product.getCategory()));
            entity.setId(createId("Products", product.getId()));
            entityCollection.getEntities().add(entity);
        }
        
        return entityCollection;
    }
    
    private Entity getProduct(List<UriParameter> keyPredicates) {
        UriParameter key = keyPredicates.get(0);
        Long productId = Long.parseLong(key.getText());
        
        return productService.getProductById(productId)
                .map(product -> {
                    Entity entity = new Entity();
                    entity.addProperty(new Property(null, "Id", ValueType.PRIMITIVE, product.getId()));
                    entity.addProperty(new Property(null, "Name", ValueType.PRIMITIVE, product.getName()));
                    entity.addProperty(new Property(null, "Description", ValueType.PRIMITIVE, product.getDescription()));
                    entity.addProperty(new Property(null, "Price", ValueType.PRIMITIVE, product.getPrice()));
                    entity.addProperty(new Property(null, "Quantity", ValueType.PRIMITIVE, product.getQuantity()));
                    entity.addProperty(new Property(null, "Category", ValueType.PRIMITIVE, product.getCategory()));
                    entity.setId(createId("Products", product.getId()));
                    return entity;
                })
                .orElse(null);
    }
    
    private URI createId(String entitySetName, Object id) {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("Unable to create URI", e);
        }
    }
    
}
