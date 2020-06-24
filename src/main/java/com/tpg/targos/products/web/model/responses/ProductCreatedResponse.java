package com.tpg.targos.products.web.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public final class ProductCreatedResponse {
    private String productReference;
}
