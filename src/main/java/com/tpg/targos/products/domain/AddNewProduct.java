package com.tpg.targos.products.domain;

import com.tpg.targos.products.web.model.requests.NewProductRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

public interface AddNewProduct {
    Optional<Product> addProduct(NewProductRequest request);

    @Value
    @AllArgsConstructor(access = PRIVATE)
    class AddNewProductCommand {
        private Product product;
    }
}
