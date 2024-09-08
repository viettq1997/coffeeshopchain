package com._digital.coffeeshopchain.controller.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlaceOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Place an order: "
            + "givenOrderCreationRequest"
            + "_callOrderCreationAPI"
            + "_shouldReturnCreatedAndOrderId")
    public void givenOrderCreationRequest_callOrderCreationAPI_shouldReturnCreatedAndOrderId() throws Exception {
        String request = """
                {
                    "customerId": "e410f94b-0d87-484a-abf6-9b6f958f3e28",
                    "shopId": "98fca19c-35e1-46f2-a968-6faa7b8fcdde",
                    "orderItems": [
                        {
                            "itemId": "123",
                            "name": "shopee",
                            "quantity": 1
                        }
                    ]
                }""";
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Place order successful!"))
                .andExpect(jsonPath("$.data.orderId").isNotEmpty());
    }

    @Test
    @DisplayName("Place an order: "
            + "givenOrderCreationMissingCustomerIdRequest"
            + "_callOrderCreationAPI"
            + "_shouldReturnBadRequest")
    public void givenOrderCreationMissingCustomerIdRequest_callOrderCreationAPI_shouldReturnBadRequest() throws Exception {
        String request = """
                {
                    "shopId": "98fca19c-35e1-46f2-a968-6faa7b8fcdde",
                    "orderItems": [
                        {
                            "itemId": "123",
                            "name": "shopee",
                            "quantity": 1
                        }
                    ]
                }""";
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Customer id is mandatory field"))
                .andExpect(jsonPath("$.code").value("CF1000"));
    }

    @Test
    @DisplayName("Place an order: "
            + "givenOrderCreationRequestWithShopNotExist"
            + "_callOrderCreationAPI"
            + "_shouldReturnShopNotFound")
    public void givenOrderCreationRequestWithShopNotExist_callOrderCreationAPI_shouldReturnShopNotFound() throws Exception {
        String request = """
                {
                    "customerId": "e410f94b-0d87-484a-abf6-9b6f958f3e28",
                    "shopId": "shop_id_not_exist",
                    "orderItems": [
                        {
                            "itemId": "123",
                            "name": "shopee",
                            "quantity": 1
                        }
                    ]
                }""";
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Shop not found"))
                .andExpect(jsonPath("$.code").value("CF1001"));
    }

    @Test
    @DisplayName("Place an order: "
            + "givenOrderCreationRequestWithCustomerNotExist"
            + "_callOrderCreationAPI"
            + "_shouldReturnCustomerNotFound")
    public void givenOrderCreationRequestWithCustomerNotExist_callOrderCreationAPI_shouldReturnCustomerNotFound() throws Exception {
        String request = """
                {
                    "customerId": "customer_not_exist",
                    "shopId": "98fca19c-35e1-46f2-a968-6faa7b8fcdde",
                    "orderItems": [
                        {
                            "itemId": "123",
                            "name": "shopee",
                            "quantity": 1
                        }
                    ]
                }""";
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer not found"))
                .andExpect(jsonPath("$.code").value("CF1002"));
    }

    @Test
    @DisplayName("Place an order: "
            + "givenOrderCreationRequestWithoutBody"
            + "_callOrderCreationAPI"
            + "_shouldReturnUncategorizedError")
    public void givenOrderCreationRequestWithoutBody_callOrderCreationAPI_shouldReturnUncategorizedError() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Uncategorized error"))
                .andExpect(jsonPath("$.code").value("API9999"));
    }
}
