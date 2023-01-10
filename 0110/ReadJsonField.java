package com.sg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ReadJsonField {
    public static void main(String[] args) throws IOException {
        //jackson objectMapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();

        //JsonNode 생성(readValue)
        JsonNode jsonNode = objectMapper.readTree(new File("/Users/jeongseung-gu/intelliJoe/JAVA/2023.01/JsonNode/jsonNodeTest/src/main/java/com/sg/lecture.json"));

        //JsonNode 읽기
        System.out.println(jsonNode);
        readJsonNodeWithGet(jsonNode);
        readJsonNodeWithPath(jsonNode);
        readJsonNodeWithAt(jsonNode);
    }

    public static void readJsonNodeWithGet(JsonNode jsonNode){
        int lectureId = jsonNode.get("id").asInt();
        String lectureName = jsonNode.get("title").asText();
        String professorName = jsonNode.get("professor").get("name").asText();
        String studentName1 = jsonNode.get("students").get(0).get("name").asText();
        String studentName2 = jsonNode.get("students").get(1).get("name").asText();

        System.out.println("----- get() -----");
        System.out.println(lectureId); //1
        System.out.println(lectureName); //Java
        System.out.println(professorName); //Kim
        System.out.println(studentName1); //Anna
        System.out.println(studentName2); //Brian
    }

    public static void readJsonNodeWithPath(JsonNode jsonNode){
        int lectureId = jsonNode.path("id").asInt();
        String lectureName = jsonNode.path("title").asText();
        String professorName = jsonNode.path("professor").get("name").asText();
        String studentName1 = jsonNode.path("students").get(0).get("name").asText();
        String studentName2 = jsonNode.path("students").get(1).get("name").asText();

        System.out.println("----- path() -----");
        System.out.println(lectureId); //1
        System.out.println(lectureName); //Java
        System.out.println(professorName); //Kim
        System.out.println(studentName1); //Anna
        System.out.println(studentName2); //Brian
    }

    public static void readJsonNodeWithAt(JsonNode jsonNode){
        int lectureId = jsonNode.at("/id").asInt();
        String lectureName = jsonNode.at("/title").asText();
        String professorName = jsonNode.at("/professor/name").asText();
        String studentName1 = jsonNode.at("/students/0/name").asText();
        String studentName2 = jsonNode.at("/students/1/name").asText();

        System.out.println("----- at() -----");
        System.out.println(lectureId); // 1
        System.out.println(lectureName); // Java
        System.out.println(professorName); // Kim
        System.out.println(studentName1); // Anna
        System.out.println(studentName2); // Brian
    }
}
