package com.tpg.targos.products.web.model.requests;

import com.tpg.targos.products.domain.ManufacturerReference;
import com.tpg.targos.products.domain.ProductType;
import lombok.*;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor
public final class NewProductRequest {
    private String name;
    private String description;
    private ProductType productType;
    private String stockReference;
    private ManufacturerReference manufacturerReference;
    private String currency;
    private BigDecimal unitPrice;
    private int quantity;
}
