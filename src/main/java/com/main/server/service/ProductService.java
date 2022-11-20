package com.main.server.service;

import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product saveProduct(Product product, Long orgId) throws ResourceAlreadyExistException;

    Product getProductById(Long id);

    Product updateProduct(Product product, Long orgId, Long id);

    Product deleteProduct(Long id);

    List<Product> getAllProducts(Long orgId);
}
