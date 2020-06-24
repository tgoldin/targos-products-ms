package com.tpg.targos.products.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpg.targos.products.domain.AddNewProduct;
import com.tpg.targos.products.domain.ManufacturerReference;
import com.tpg.targos.products.domain.Product;
import com.tpg.targos.products.web.model.requests.NewProductRequest;
import com.tpg.targos.products.web.model.responses.ProductCreatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static com.tpg.targos.products.domain.ManufacturerReferenceType.EAN;
import static com.tpg.targos.products.domain.ProductType.Electronics;
import static com.tpg.targos.products.domain.ProductsUuids.NEW_UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ExtendWith(MockitoExtension.class)
public class ProductsCommandControllerTests {
    private ObjectMapper objectMapper;

    private NewProductRequest newProductRequest;

    @Mock
    private AddNewProduct addNewProduct;

    @InjectMocks
    private ProductsCommandController controller;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        newProductRequest = NewProductRequest.builder()
                .name("Sony A6000 Mirrorless Camera")
                .description("Sony A6000 mirrorless camera with 16-50mm lens")
                .productType(Electronics)
                .stockReference("554-7207")
                .manufacturerReference(ManufacturerReference
                        .builder()
                            .reference("4905524974409")
                            .manufacturerReferenceType(EAN)
                        .build())
                .currency("£")
                .unitPrice(new BigDecimal(375.00))
                .build();
    }

    @Test
    public void givenANewProduct_whenPosted_thenProductIsSavedAndProductCreatedResponseIsReturned() throws JsonProcessingException {
        Product product = buildProduct();
        
        when(addNewProduct.addProduct(newProductRequest)).thenReturn(Optional.of(product));

        ResponseEntity<ProductCreatedResponse> actual = controller.postProduct(newProductRequest);

        assertThat(actual.getStatusCode()).isEqualTo(CREATED);

        assertThat(actual.getBody()).asString().contains("productReference");
        assertThat(actual.getBody()).asString().contains(NEW_UUID);
        assertThat(actual.getHeaders().get("Location")).hasSize(1);
        assertThat(actual.getHeaders().get("Location")).contains(String.format("/products/electronics/%s", NEW_UUID));
    }

    @Test
    public void givenaNewProduct_whenPostingNewProductFails_thenInternalServerErrorStatusIsReturned() {
        when(addNewProduct.addProduct(newProductRequest)).thenReturn(Optional.empty());

        ResponseEntity<ProductCreatedResponse> actual = controller.postProduct(newProductRequest);

        assertThat(actual.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody()).isEqualTo(null);

        assertThat(actual.getHeaders()).isEmpty();
    }

    private Product buildProduct() {
        return Product.builder()
                .name(newProductRequest.getName())
                .description(newProductRequest.getDescription())
                .stockReference(newProductRequest.getStockReference())
                .productType(newProductRequest.getProductType())
                .productReference(NEW_UUID)
                .manufacturerReference(newProductRequest.getManufacturerReference())
                .unitPrice(newProductRequest.getUnitPrice())
                .quantity(newProductRequest.getQuantity())
                .build();
    }
}
