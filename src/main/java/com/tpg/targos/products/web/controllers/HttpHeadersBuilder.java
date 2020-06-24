package com.tpg.targos.products.web.controllers;

import com.tpg.targos.products.domain.ProductType;
import org.springframework.http.HttpHeaders;

public interface HttpHeadersBuilder {
    String HEADER_LOCATION_KEY = "Location";

    default HttpHeaders generateHttpHeaders(String uri, ProductType productType, String uuid) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (uuid == null) { return httpHeaders; }

        httpHeaders.add(HEADER_LOCATION_KEY, String.format("%s/%s/%s", uri, productType.name().toLowerCase(), uuid));

        return httpHeaders;
    }
}
