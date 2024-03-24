package com.mauro.curso.springboot.app.springbootcrud.repositories;

import com.mauro.curso.springboot.app.springbootcrud.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    boolean existsBySku(String sku);
}
