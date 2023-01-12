package com.sg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateJsonNode {
    public static void main(String[] args) {

        // ObjectMapper 생성
        ObjectMapper mapper = new ObjectMapper();

        // JsonNode 생성
        JsonNode jsonNode = mapper.createObjectNode();
    }
}
