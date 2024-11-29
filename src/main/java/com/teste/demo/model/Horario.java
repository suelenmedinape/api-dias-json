package com.teste.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Horario {
    // esse campo está no json
    private Map<String, List<String>> times;
}
