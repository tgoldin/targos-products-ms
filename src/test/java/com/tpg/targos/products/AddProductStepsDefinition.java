package com.tpg.targos.products;

import com.tpg.targos.products.domain.ManufacturerReference;
import com.tpg.targos.products.domain.ManufacturerReferenceType;
import com.tpg.targos.products.domain.ProductType;
import com.tpg.targos.products.web.model.requests.NewProductRequest;
import com.tpg.targos.products.web.model.responses.ProductCreatedResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

public class AddProductStepsDefinition {
    private static final String URL = "https://targos.dev/products";

    private RestTemplate addProductRestClient;

    private NewProductRequest newProductRequest;

    private ResponseEntity<ProductCreatedResponse> response;

    @Given("I am a product buyer {string}")
    public void i_am_a_product_buyer(String value) {
        String user = value.trim();

        addProductRestClient = new RestTemplateBuilder()
                .basicAuthentication(user, String.format("%s-dev-dev", user))
                .setConnectTimeout(Duration.ofMillis(5000))
                .build();
    }

    @And("a new product is available")
    public void a_new_product_is_available(DataTable table) {
        List<String> row = table.row(1);

        toRequest(row);
    }

    private void toRequest(List<String> row) {
        newProductRequest = NewProductRequest.builder()
                .name(row.get(0))
                .description(row.get(1))
                .productType(toProductType(row.get(2)))
                .stockId(row.get(3))
                .manufacturerReference(toManufacturerReference(row.get(4), row.get(5)))
                .currency(row.get(6))
                .unitPrice(new BigDecimal(row.get(7)))
                .quantity(toQuantity(row.get(8)))
                .build();

    }

    private ProductType toProductType(String value) {
        ProductType found = ProductType.valueOf(value);

        if (found == null) { throw new RuntimeException(String.format("%s not found as product type", value)); }

        return found;
    }

    private ManufacturerReference toManufacturerReference(String refType, String reference) {
        ManufacturerReferenceType foundType = ManufacturerReferenceType.valueOf(refType);

        if (foundType == null) { throw new RuntimeException(String.format("% not found", refType)); }

        return ManufacturerReference.builder()
                .reference(reference)
                .manufacturerReferenceType(foundType)
                .build();
    }
    private Integer toQuantity(String value) {
        return Integer.parseInt(value);
    }

    @When("I add the new product")
    public void add_the_new_product() {
        response = addProductRestClient.postForEntity(URL, newProductRequest, ProductCreatedResponse.class);
    }

    @Then("the product is available for purchase via the web channel")
    public void product_available_on_web_channel() {
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        assertThat(response.getBody().getProductReferenceId()).isNotNull();
    }
}
