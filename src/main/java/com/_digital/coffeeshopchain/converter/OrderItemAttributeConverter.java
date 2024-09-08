package com._digital.coffeeshopchain.converter;

import com._digital.coffeeshopchain.domain.order.OrderItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Converter(autoApply = true)
@Slf4j
public class OrderItemAttributeConverter implements AttributeConverter<List<OrderItem>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(List orderItems) {
        try {
            return objectMapper.writeValueAsString(orderItems);
        } catch (JsonProcessingException jpe) {
            log.warn("Cannot convert order items into JSON");
            return null;
        }
    }

    @Override
    public List<OrderItem> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, objectMapper.getTypeFactory().constructCollectionType(List.class, OrderItem.class));
        } catch (JsonProcessingException e) {
            log.warn("Cannot convert JSON into Order items");
            return null;
        }
    }
}
