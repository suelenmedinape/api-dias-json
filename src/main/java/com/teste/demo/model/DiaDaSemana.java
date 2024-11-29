package com.teste.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dias_semana")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// cricao do banco de dados para armazenar as datas referentes a cada professor
public class DiaDaSemana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // é o relaciomento entre o banco de dados dias da semana e professores
    @ManyToOne
    // foreingkey
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    // construtor
    public DiaDaSemana(String name, Professor professor) {
        this.name = name;
        this.professor = professor;
    }
}
