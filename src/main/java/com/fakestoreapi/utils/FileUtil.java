package com.fakestoreapi.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



// Utility class for common file operations.

public class FileUtil {


    public static String readFileAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }


    public static byte[] readFileAsBytes(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }


    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }


    public static FileInputStream getFileInputStream(String filePath) throws IOException {
        return new FileInputStream(new File(filePath));
    }
}
