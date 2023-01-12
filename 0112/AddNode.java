package com.sg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class AddNode {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        addPrimitiveType(mapper);
        addNewNode(mapper);
    }

    private static void addPrimitiveType(ObjectMapper mapper) throws IOException {
        //lecture JSON 읽기
        ObjectNode lectureNode = (ObjectNode) mapper.readTree(new File("/Users/jeongseung-gu/intelliJoe/JAVA/2023.01/JsonNode/jsonNodeTest/src/main/java/com/sg/lecture.json"));

        //primitive type 추가
        lectureNode.put("classroom", 101);

        //결과 출력
        System.out.println("-------classroom 노드 추가 ---------");
        System.out.println(lectureNode);
    }

    private static void addNewNode(ObjectMapper mapper) throws IOException {
        //lecture JSON 읽기
        ObjectNode lectureNode = (ObjectNode) mapper.readTree(new File("/Users/jeongseung-gu/intelliJoe/JAVA/2023.01/JsonNode/jsonNodeTest/src/main/java/com/sg/lecture.json"));

        //Object Node 생성
        ObjectNode contact = mapper.createObjectNode();
        contact.put("tel", "010-1111-1111");
        contact.put("email", "abc@abc.com");

        lectureNode.set("contact", contact);

        //결과출력
        System.out.println("-------contact 노드 추가 ---------");
        System.out.println(lectureNode);
    }
}
