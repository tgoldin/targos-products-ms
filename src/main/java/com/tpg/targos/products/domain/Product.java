package com.tpg.targos.products.domain;

import com.tpg.targos.products.web.model.requests.NewProductRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Builder
@Value
public class Product {
    public static Product product(NewProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .productType(request.getProductType())
                .stockReference(request.getStockReference())
                .unitPrice(request.getUnitPrice())
                .quantity(request.getQuantity())
                .manufacturerReference(request.getManufacturerReference())
                .build();
    }

    private String name;
    private String description;
    private String stockReference;
    private ManufacturerReference manufacturerReference;
    private ProductType productType;
    private String productReference;
    private int quantity;
    private BigDecimal unitPrice;
}
