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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get order status: "
            + "givenOrderId"
            + "_callOrderStatusAPI"
            + "_shouldReturnOrderStatus")
    public void givenOrderId_callOrderStatusAPI_shouldReturnOrderStatus() throws Exception {

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

        mockMvc.perform(get("/api/v1/orders/{orderId}/status", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Get order status successful!"))
                .andExpect(jsonPath("$.data.orderId").value(orderId))
                .andExpect(jsonPath("$.data.orderStatus").value("PLACED"))
                .andExpect(jsonPath("$.data.queuePosition").value(1));
    }

    @Test
    @DisplayName("Get order status: "
            + "givenOrderIdNotExist"
            + "_callOrderStatusAPI"
            + "_shouldReturnOrderNotFound")
    public void givenOrderIdNotExist_callOrderStatusAPI_shouldReturnOrderNotFound() throws Exception {
        String orderId = "order_id_not_exist";

        mockMvc.perform(get("/api/v1/orders/{orderId}/status", orderId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Order not found"))
                .andExpect(jsonPath("$.code").value("CF1003"));
    }
}
