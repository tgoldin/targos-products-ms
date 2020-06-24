package com.tpg.targos.products.web.controllers;

import com.tpg.targos.products.domain.AddNewProduct;
import com.tpg.targos.products.domain.Product;
import com.tpg.targos.products.web.model.requests.NewProductRequest;
import com.tpg.targos.products.web.model.responses.ProductCreatedResponse;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@Value
public class ProductsCommandController implements HttpHeadersBuilder {
    private static final String URI = "/products";

    private AddNewProduct addNewProduct;

    public ResponseEntity<ProductCreatedResponse> postProduct(NewProductRequest request) {
        Optional<Product> product = addNewProduct.addProduct(request);

        Optional<ProductCreatedResponse> productCreatedResponse = product.map(p -> ProductCreatedResponse.builder()
                .productReference(p.getProductReference()).build());

        return productCreatedResponse
            .map(pcr -> ResponseEntity
                .status(CREATED)
                .headers(generateHttpHeaders(URI, product.get().getProductType(), product.get().getProductReference()))
                .body(pcr))
            .orElse(ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .headers(generateHttpHeaders(URI, request.getProductType(), null))
                    .body(null));
    }
}
