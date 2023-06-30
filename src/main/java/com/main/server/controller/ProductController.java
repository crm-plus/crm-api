package com.main.server.controller;

import com.main.server.model.Product;
import com.main.server.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = {"/api/organizations/{organizationId}"})
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PreAuthorize("hasPermission(#product.organization().name(), 'edit')")
    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, @PathVariable @NotNull Long organizationId) {
        return ResponseEntity.ok(productService.saveProduct(product, organizationId));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PreAuthorize("hasPermission(#product.organization().name(), 'edit')")
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product,
                                                 @PathVariable @NotNull Long organizationId,
                                                 @PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productService.updateProduct(product, organizationId, id));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable @NotNull Long organizationId) {
        return ResponseEntity.ok(productService.getAllProducts(organizationId));
    }
}
