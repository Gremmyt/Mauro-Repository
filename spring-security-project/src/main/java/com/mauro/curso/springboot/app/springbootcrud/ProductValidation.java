package com.mauro.curso.springboot.app.springbootcrud;

import com.mauro.curso.springboot.app.springbootcrud.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

@Component
public class ProductValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "is required!");
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotBlank.product.description");
        if (product.getDescription() == null || product.getDescription().isBlank()) {
            errors.rejectValue("description", null,"is required, please");
        }

        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotNull.product.price");
        if (product.getPrice() == null)  {
            errors.rejectValue("price", null,"can not be null");
        } else if (product.getPrice() <500) {
            errors.rejectValue("price",null,"must be a numerical value greater than 500!");
        }


    }
}
