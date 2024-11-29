package com.teste.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfessorDTO {
    // define quais dados devem ser exibidos
    private Long id;
    private String nome;
    private Integer matricula;
    private String email;
}
