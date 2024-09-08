package com._digital.coffeeshopchain.controller.order;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CancelOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Cancel an order: "
            + "givenOrderId"
            + "_callCancelOrderAPI"
            + "_shouldReturnCancelSuccess")
    public void givenOrderId_callCancelOrderAPI_shouldReturnCancelSuccess() throws Exception {

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
        MvcResult result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andReturn();
        String responseBody = result.getResponse().getContentAsString();

        String orderId = JsonPath.read(responseBody, "$.data.orderId");

        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cancel order successful!"))
                .andExpect(jsonPath("$.data.orderId").value(orderId));
    }

    @Test
    @DisplayName("Cancel an order: "
            + "givenOrderId"
            + "_callCancelOrderAPI2Times"
            + "_shouldReturnBadRequestCannotCancelAtSecondTime")
    public void givenOrderId_callCancelOrderAPI2Times__shouldReturnBadRequestCannotCancelAtSecondTime() throws Exception {

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
        MvcResult result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andReturn();
        String responseBody = result.getResponse().getContentAsString();

        String orderId = JsonPath.read(responseBody, "$.data.orderId");

        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cancel order successful!"))
                .andExpect(jsonPath("$.data.orderId").value(orderId));

        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Only placed status can be cancel!"))
                .andExpect(jsonPath("$.code").value("CF1004"));
    }

    @Test
    @DisplayName("Cancel an order: "
            + "givenOrderIdNotExist"
            + "_callCancelOrderAPI"
            + "_shouldReturnOrderNotFound")
    public void givenOrderIdNotExist_callCancelOrderAPI_shouldReturnOrderNotFound() throws Exception {
        String orderId = "order_id_not_exist";

        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Order not found"))
                .andExpect(jsonPath("$.code").value("CF1003"));
    }
}
