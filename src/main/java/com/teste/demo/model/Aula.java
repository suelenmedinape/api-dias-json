package com.teste.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.List;

@Getter
@Setter
public class Aula {
    // esses campos estao no json
    private String materia;
    private Map<String, DiaAula> semana;
}