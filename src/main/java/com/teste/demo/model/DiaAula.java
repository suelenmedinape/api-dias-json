package com.teste.demo.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiaAula {
    // esse campo está no json
    private List<String> times;
}
