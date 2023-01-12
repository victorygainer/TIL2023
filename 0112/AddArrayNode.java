package com.sg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;

public class AddArrayNode {
    public static void main(String[] args) {

        //Object Mapper 생성
        ObjectMapper mapper = new ObjectMapper();

        arrayNodeForPrimitiveType(mapper);
        arrayNodeForJsonNode(mapper);

    }

    private static void arrayNodeForPrimitiveType(ObjectMapper mapper) {
        //ObjectNode 생성
        ObjectNode objectNode = mapper.createObjectNode();

        //ArrayNode 생성
        ArrayNode arr = mapper.createArrayNode();
        //ArrayNode에 primitive type element 추가
        arr.add("a");
        arr.add("b");
        arr.add("c");

        //ObjectNode에 ArrayNode 추가
        objectNode.set("alphabet", arr);

        //결과출력
        System.out.println("------- ArrayNode(Primitive Type) ---------");
        System.out.println(objectNode);
        System.out.println(objectNode.get("alphabet"));
    }

    private static void arrayNodeForJsonNode(ObjectMapper mapper) {
        //ObjectNode 생성
        ObjectNode objectNode = mapper.createObjectNode();

        //Array에 들어갈 element 생성
        ObjectNode element1 = mapper.createObjectNode();
        element1.put("id", 1);
        element1.put("name", "Anna");

        ObjectNode element2 = mapper.createObjectNode();
        element2.put("id", 2);
        element2.put("name", "Amelia");

        ObjectNode element3 = mapper.createObjectNode();
        element3.put("id", 3);
        element3.put("name", "Joe");

        //ArrayNode 생성
        ArrayNode students = mapper.createArrayNode();
        students.add(element1);
        students.addAll(Arrays.asList(element2, element3));

        //ObjectNode에 ArrayNode 추가
        objectNode.set("students", students);

        System.out.println(objectNode);
        /*System.out.println(objectNode.get("students").get(0).get("id"));
        System.out.println(objectNode.get("students").get(0).get("name").asText());*/

    }
}
