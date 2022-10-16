package com.main.server.service.impl;

import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.User;
import com.main.server.model.Organization;
import com.main.server.model.Product;
import com.main.server.repository.ProductRepository;
import com.main.server.service.ProductService;
import com.main.server.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;

    @Override
    public Product saveProduct(Product product, Long orgId) throws ResourceAlreadyExistException {
        log.info("Enter saveProduct() product: {}", product);
        checkIfProductExist(product.name(), orgId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        Organization organization =
        product.createdBy(user);
        return productRepository.save(product);//todo: need to save orgId too?
    }

    @Override
    public Product getProductById(Long id, Long orgId) {
        log.info("Enter getProductById() productId: {}", id);
        return productRepository.getByIdAndOrganizationId(id, orgId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Product with such id {%d} is not exist", id))
        );
    }

    @Override
    public Product updateProduct(Product product, Long orgId) {
        log.info("Enter updateProduct() productName: {}", product.name());
        Product exitedProduct = getProduct(product.name(), orgId);

        Product exitedProductWithSameName =  productRepository.findByNameAndOrganizationId(product.name(), orgId)
                .orElse(null);
        if(exitedProductWithSameName != null){
            throw new ResourceAlreadyExistException(
                    String.format("Product with name %s already exist in this organization", product.name()));
        }

        exitedProduct.name(product.name());
        exitedProduct.description(product.description());
        exitedProduct.price(product.price());
        exitedProduct.primeCost(product.primeCost());
        return productRepository.save(exitedProduct);
    }

    @Override
    public Product deleteProduct(Long id, Long orgId) {
        Product product = getProduct(id, orgId);
        User user = new User(); // TODO add real user
        product.deletedBy(user);
        return productRepository.save(product);
    }

    private void checkIfProductExist(String name, Long orgId) {
        Optional<Product> product = productRepository.findByNameAndOrganizationId(name, orgId);
        if (product.isPresent()) {
            throw new ResourceAlreadyExistException(
                    String.format("Product with the same name {%s} already exist", name)
            );
        }
    }

    private Product getProduct(String name, Long orgId) {
        return productRepository.findByNameAndOrganizationId(name, orgId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Product with such id {%d} and name {%s} is not exist", orgId, name))
        );
    }

    private Product getProduct(Long id, Long orgId) {
        return productRepository.getByIdAndOrganizationId(id, orgId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Product with such id {%d} is not exist", id))
        );
    }
}
