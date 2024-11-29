package com.teste.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.demo.model.HorariosJson;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

@Service
public class JsonParserService {

    private final ObjectMapper objectMapper;

    public JsonParserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public HorariosJson parseJsonFile(String filePath) {
        try {
            Path arquivo = Paths.get(filePath);
            return objectMapper.readValue(arquivo.toFile(), HorariosJson.class);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON file", e);
        }
    }

}