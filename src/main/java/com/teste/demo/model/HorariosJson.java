package com.teste.demo.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Getter
public class HorariosJson {
    // Esse campo pega o nome do professor que esta no json para fazer a comparação com o banco de dados
    private Map<String, List<Aula>> professores = new HashMap<>();


    /* Anotação que informa ao Jackson que este método deve ser usado para definir qualquer propriedade JSON */
    @JsonAnySetter
    /* Método que adiciona uma entrada ao mapa 'professores', onde a chave é o nome do professor e o valor é uma lista
          de objetos 'Aula' */
    public void setProfessor(String nome, List<Aula> aulas) {
        // Adiciona o nome do professor e a lista de aulas ao mapa 'professores'
        professores.put(nome, aulas);
    }
}