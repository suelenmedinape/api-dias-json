package com.teste.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Table(name = "tb_professores")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer matricula;
    private String email;
    private String senha;

    // é o relaciomento entre o banco de dados professore e dias da semana
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    // usado para evitar um loop infinito
    private Set<DiaDaSemana> workDays = new HashSet<>();

    // construtor
    public Professor(String nome, Integer matricula, String email, String senha) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.senha = senha;
    }
    public void addWorkDay(DiaDaSemana day) {
        workDays.add(day);
        day.setProfessor(this);
    }

    public void removeWorkDay(DiaDaSemana day) {
        workDays.remove(day);
        day.setProfessor(null);
    }
}
