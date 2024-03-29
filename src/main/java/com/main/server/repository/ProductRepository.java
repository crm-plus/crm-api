package com.main.server.repository;

import com.main.server.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByNameAndOrganizationId(String name, Long id);
    List<Product> findAllByOrganizationIdAndDeletedByNull(Long orgId);
}
