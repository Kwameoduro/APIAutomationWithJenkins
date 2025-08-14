package com.fakestoreapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


 //  Utility class for JSON serialization and deserialization.

public class JsonUtil {

    // Singleton ObjectMapper instance for performance
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
        // Prevent instantiation
    }


    public static String toJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing object to JSON", e);
        }
    }


    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing JSON string", e);
        }
    }


    public static <T> T fromJsonFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON from file: " + filePath, e);
        }
    }


    public static void toJsonFile(Object object, String filePath) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), object);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON to file: " + filePath, e);
        }
    }


    public static String readJsonFileAsString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file as string: " + filePath, e);
        }
    }
}
