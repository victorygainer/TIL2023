package com.sg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class CreateArrayNode {
    public static void main(String[] args) {
        //ObjectMapper 생성
        ObjectMapper mapper = new ObjectMapper();

        //ArrayNode 생성
        ArrayNode arrayNode = mapper.createArrayNode();
    }
}
