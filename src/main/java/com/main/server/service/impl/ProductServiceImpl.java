package com.main.server.service.impl;

import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.User;
import com.main.server.model.Organization;
import com.main.server.model.Product;
import com.main.server.repository.OrganizationRepository;
import com.main.server.repository.ProductRepository;
import com.main.server.repository.UserRepository;
import com.main.server.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl extends AbstractService implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(UserRepository userRepository,
                              OrganizationRepository organizationRepository,
                              ProductRepository productRepository) {
        super(userRepository, organizationRepository);
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product, Long orgId) throws ResourceAlreadyExistException {
        log.info("Enter saveProduct() product: {}", product);
        checkIfProductExist(product.name(), orgId);
        User user = getAuthenticatedUser();
        Organization organization = findOrganization(orgId);
        product.createdBy(user);
        product.organization(organization);
        //todo add tags and custom attributes
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Enter getProductById() productId: {}", id);
        return productRepository.getById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Product with such id {%d} is not exist", id))
        );
    }

    @Override
    public Product updateProduct(Product product, Long orgId) {
        log.info("Enter updateProduct() productName: {}", product.name());
        Product exitedProduct = getProduct(product.getId());

        Product exitedProductWithSameName = productRepository.findByNameAndOrganizationId(product.name(), orgId)
                .orElse(null);
        if (exitedProductWithSameName != null) {
            throw new ResourceAlreadyExistException(
                    String.format("Product with name %s already exist in this organization", product.name()));
        }

        exitedProduct.name(product.name());
        exitedProduct.description(product.description());
        exitedProduct.price(product.price());
        exitedProduct.primeCost(product.primeCost());
        //todo add tags and custom attributes
        return productRepository.save(exitedProduct);
    }

    @Override
    public Product deleteProduct(Long id) {
        log.info("Enter deleteProduct() id: {}", id);
        Product product = getProduct(id);
        User user = getAuthenticatedUser();
        product.deletedBy(user);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts(Long orgId) {
        log.info("Enter getAllProducts() orgId: {}", orgId);
        return productRepository.findAllByOrganizationIdAndDeletedByNull(orgId);
    }

    private void checkIfProductExist(String name, Long orgId) {
        Optional<Product> product = productRepository.findByNameAndOrganizationId(name, orgId);
        if (product.isPresent()) {
            throw new ResourceAlreadyExistException(
                    String.format("Product with the same name {%s} already exist", name)
            );
        }
    }

    private Product getProduct(Long id) {
        return productRepository.getById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Product with such id {%d} is not exist", id))
        );
    }
}
