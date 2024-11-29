package com.teste.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ProfessorDiasAulasDTO {
    // define quais dados devem ser exibidos
    private String nome;
    private Set<String> diasDaSemana;
}
