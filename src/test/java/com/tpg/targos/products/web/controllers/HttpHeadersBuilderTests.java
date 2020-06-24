package com.tpg.targos.products.web.controllers;

import com.tpg.targos.products.domain.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static com.tpg.targos.products.domain.ProductType.Electronics;
import static com.tpg.targos.products.domain.ProductsUuids.NEW_UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersBuilderTests implements HttpHeadersBuilder {
    private static final String URI = "/products";

    private static final ProductType PRODUCT_TYPE = Electronics;

    @Test
    public void givenAUuid_whenBuilt_headersContainLocationValue() {
        HttpHeaders actual = generateHttpHeaders(URI, PRODUCT_TYPE, NEW_UUID);
        List<String> actualList = actual.get("Location");

        assertThat(actualList.size()).isEqualTo(1);
        assertThat(actualList.get(0)).isEqualTo(String.format("%s/%s/%s", URI, PRODUCT_TYPE.name().toLowerCase(), NEW_UUID));
    }

    @Test
    public void givenNullUuid_whenBuilt_headersIsEmpty() {
        HttpHeaders actual = generateHttpHeaders(URI, PRODUCT_TYPE, null);
        List<String> actualList = actual.get("Location");

        assertThat(actual).isEmpty();
    }
}
