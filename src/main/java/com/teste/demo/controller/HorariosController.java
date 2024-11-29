package com.teste.demo.controller;

import com.teste.demo.model.HorariosJson;
import com.teste.demo.service.JsonParserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorariosController {

    private final JsonParserService jsonParserService;

    public HorariosController(JsonParserService jsonParserService) {
        this.jsonParserService = jsonParserService;
    }

    @GetMapping
    public HorariosJson getHorarios() {
        try {
            return jsonParserService.parseJsonFile("C:\\Java Spring\\json\\schedule10.json");
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON file", e);
        }
    }
}