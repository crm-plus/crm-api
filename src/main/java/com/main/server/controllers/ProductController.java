package com.main.server.controllers;

import com.main.server.model.Product;
import com.main.server.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(path = {"/api/organizations/{orgId}"})
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, @PathVariable @NotNull Long orgId) {
        return ResponseEntity.ok(productService.saveProduct(product, orgId));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable @NotNull Long orgId) {
        return ResponseEntity.ok(productService.updateProduct(product, orgId));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable @NotNull Long orgId) {
        return ResponseEntity.ok(productService.getAllProducts(orgId));
    }
}
