package com.lcaohoanq.odataapi.servlet;

import com.lcaohoanq.odataapi.model.Product;
import com.lcaohoanq.odataapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/odata.svc")
@RequiredArgsConstructor
@Slf4j
public class ODataServlet {
    
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getServiceDocument() {
        log.info("OData service document requested");
        Map<String, Object> response = new HashMap<>();
        response.put("@odata.context", "$metadata");
        response.put("value", List.of(
                Map.of("name", "Products", "kind", "EntitySet", "url", "Products")
        ));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/$metadata")
    public ResponseEntity<String> getMetadata() {
        log.info("OData metadata requested");
        String metadata = """
                <?xml version="1.0" encoding="utf-8"?>
                <edmx:Edmx Version="4.0" xmlns:edmx="http://docs.oasis-open.org/odata/ns/edmx">
                  <edmx:DataServices>
                    <Schema Namespace="com.lcaohoanq.odata" xmlns="http://docs.oasis-open.org/odata/ns/edm">
                      <EntityType Name="Product">
                        <Key><PropertyRef Name="Id"/></Key>
                        <Property Name="Id" Type="Edm.Int64" Nullable="false"/>
                        <Property Name="Name" Type="Edm.String"/>
                        <Property Name="Description" Type="Edm.String"/>
                        <Property Name="Price" Type="Edm.Double"/>
                        <Property Name="Quantity" Type="Edm.Int32"/>
                        <Property Name="Category" Type="Edm.String"/>
                      </EntityType>
                      <EntityContainer Name="Container">
                        <EntitySet Name="Products" EntityType="com.lcaohoanq.odata.Product"/>
                      </EntityContainer>
                    </Schema>
                  </edmx:DataServices>
                </edmx:Edmx>
                """;
        return ResponseEntity.ok()
                .header("Content-Type", "application/xml")
                .body(metadata);
    }
    
    @GetMapping("/Products")
    public ResponseEntity<Map<String, Object>> getProducts() {
        log.info("OData Products collection requested");
        List<Product> products = productService.getAllProducts();
        
        Map<String, Object> response = new HashMap<>();
        response.put("@odata.context", "$metadata#Products");
        response.put("value", products);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/Products({id})")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Long id) {
        log.info("OData Product requested: {}", id);
        return productService.getProductById(id)
                .map(product -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("@odata.context", "$metadata#Products/$entity");
                    response.put("Id", product.getId());
                    response.put("Name", product.getName());
                    response.put("Description", product.getDescription());
                    response.put("Price", product.getPrice());
                    response.put("Quantity", product.getQuantity());
                    response.put("Category", product.getCategory());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
}
