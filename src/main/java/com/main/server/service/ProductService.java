package com.main.server.service;

import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.model.organization.Organization;
import com.main.server.model.product.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Product saveProduct(Product product, Long orgId) throws ResourceAlreadyExistException;

    Product getProductById(Long id, Long orgId);

    Product updateProduct(Product product, Long orgId);

    Product deleteProduct(Long id, Long orgId);
}
