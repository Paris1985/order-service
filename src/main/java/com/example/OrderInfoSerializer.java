package com.example;

import com.example.order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class OrderInfoSerializer implements Serializer<Order>

    {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

        @Override
        public byte[] serialize(String s, Order order) {
        try {
            if (order == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(order);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing MessageDto to byte[]");
        }
    }

        @Override
        public byte[] serialize(String topic, Headers headers, Order data) {
        return Serializer.super.serialize(topic, headers, data);
    }

        @Override
        public void close() {
        Serializer.super.close();
    }
    }