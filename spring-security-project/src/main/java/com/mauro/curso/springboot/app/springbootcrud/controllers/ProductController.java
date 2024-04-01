package com.mauro.curso.springboot.app.springbootcrud.controllers;

import com.mauro.curso.springboot.app.springbootcrud.ProductValidation;
import com.mauro.curso.springboot.app.springbootcrud.entities.Product;
import com.mauro.curso.springboot.app.springbootcrud.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService service;

/*    private ProductValidation validation;*/

    /**
     * Endpoint used to get the products in a database.
     * @return List of all available products in the database.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Product> list(){
        return service.findAll();
    }

    /**
     * Endpoint used to get a specific product by its ID.
     * @param id in the endpoint's path.
     * @return product if exists or http status 404 if not.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> view (@PathVariable Long id){
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint used to create a newc product.
     * @param product used to create a product.
     * @param result used to handle validations.
     * @return created product or validation message.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(
            @Valid @RequestBody Product product, BindingResult result){
        /*validation.validate(product, result);*/
        if (result.hasFieldErrors()){
            return validation(result);
        }
        Product productNew = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productNew);
    }

    /**
     * Endpoint used to do a product's update.
     * @param product with the update information.
     * @param result used to handle validations.
     * @param id in the endpoint's path.
     * @return updated product or Http Status 404 not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){
        /*validation.validate(product, result);*/
        if (result.hasFieldErrors()){
            return validation(result);
        }
        Optional<Product> productOptional = service.update(id, product);
        if (productOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint used to delete a Product
     * @param id in the endpoint's path.
     * @return Http status Ok or Not found.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Product product = new Product();
        product.setId(id);
        Optional<Product> productOptional = service.delete(id);
        if (productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(), "The field "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);

    }


}
