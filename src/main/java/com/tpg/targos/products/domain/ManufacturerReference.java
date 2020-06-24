package com.tpg.targos.products.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class ManufacturerReference {
    private String reference;
    private ManufacturerReferenceType manufacturerReferenceType;
}
