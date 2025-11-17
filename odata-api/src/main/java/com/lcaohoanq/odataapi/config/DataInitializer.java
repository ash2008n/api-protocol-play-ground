package com.lcaohoanq.odataapi.config;

import com.lcaohoanq.odataapi.model.Product;
import com.lcaohoanq.odataapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            log.info("Initializing sample product data...");
            
            productRepository.saveAll(Arrays.asList(
                    Product.builder()
                            .name("Laptop")
                            .description("High-performance laptop")
                            .price(1299.99)
                            .quantity(50)
                            .category("Electronics")
                            .build(),
                    
                    Product.builder()
                            .name("Mouse")
                            .description("Wireless mouse")
                            .price(29.99)
                            .quantity(200)
                            .category("Electronics")
                            .build(),
                    
                    Product.builder()
                            .name("Keyboard")
                            .description("Mechanical keyboard")
                            .price(89.99)
                            .quantity(150)
                            .category("Electronics")
                            .build(),
                    
                    Product.builder()
                            .name("Book - Java Programming")
                            .description("Learn Java programming")
                            .price(49.99)
                            .quantity(100)
                            .category("Books")
                            .build(),
                    
                    Product.builder()
                            .name("Office Chair")
                            .description("Ergonomic office chair")
                            .price(299.99)
                            .quantity(30)
                            .category("Furniture")
                            .build()
            ));
            
            log.info("Sample data initialized: {} products", productRepository.count());
        };
    }
    
}
