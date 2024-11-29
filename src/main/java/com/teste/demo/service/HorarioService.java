package com.teste.demo.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.demo.model.*;
import com.teste.demo.repository.DiaDaSemanaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    private final JsonParserService jsonParserService;
    private final ProfessorService professorService;
    private final DiaDaSemanaRepository diaDaSemanaRepository;
    private final ObjectMapper objectMapper;

    public HorarioService(JsonParserService jsonParserService, ProfessorService professorService,
                          DiaDaSemanaRepository diaDaSemanaRepository,
                          ObjectMapper objectMapper) {
        this.jsonParserService = jsonParserService;
        this.professorService = professorService;
        this.diaDaSemanaRepository = diaDaSemanaRepository;
        this.objectMapper = objectMapper;
    }

    public Map<Professor, List<Aula>> getHorariosParaProfessoresCadastrados(String jsonFilePath) {
        HorariosJson horariosJson = jsonParserService.parseJsonFile(jsonFilePath);
        List<Professor> professoresCadastrados = professorService.listarTodos();

        return professoresCadastrados.stream()
                .filter(professor -> horariosJson.getProfessores().containsKey(professor.getNome()))
                .collect(Collectors.toMap(
                        professor -> professor,
                        professor -> horariosJson.getProfessores().get(professor.getNome())
                ));
    }

    public Map<Professor, Set<String>> getWorkDaysForProfessores(String jsonFilePath) {
        HorariosJson horariosJson = jsonParserService.parseJsonFile(jsonFilePath);
        List<Professor> professoresCadastrados = professorService.listarTodos();

        Map<Professor, Set<String>> workDays = new HashMap<>();

        professoresCadastrados.forEach(professor -> {
            List<Aula> aulas = horariosJson.getProfessores().get(professor.getNome());
            if (aulas != null) {
                Set<String> days = aulas.stream()
                        .flatMap(aula -> aula.getSemana().keySet().stream())
                        .collect(Collectors.toSet());
                workDays.put(professor, days);
            }
        });

        return workDays;
    }

    public void saveWorkDays(Map<Professor, Set<String>> workDays) {
        workDays.forEach((professor, days) -> {
            days.forEach(day -> {
                DiaDaSemana dayOfWeek = new DiaDaSemana(day, professor);
                diaDaSemanaRepository.save(dayOfWeek);
            });
            professorService.salvar(professor);
        });
    }

}
