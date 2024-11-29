package com.teste.demo.controller;

import com.teste.demo.dto.ProfessorDTO;
import com.teste.demo.dto.ProfessorDiasAulasDTO;
import com.teste.demo.model.Aula;
import com.teste.demo.model.DiaDaSemana;
import com.teste.demo.model.Professor;
import com.teste.demo.service.HorarioService;
import com.teste.demo.service.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professor")
public class ProfessorController {


    private ProfessorService professorService;
    private final HorarioService horarioService;

    public ProfessorController(ProfessorService professorService, HorarioService horarioService) {
        this.professorService = professorService;
        this.horarioService = horarioService;
    }

    @GetMapping
    public List<ProfessorDTO> listarTodos() {
        List<Professor> professores = professorService.listarTodos();
        return professores.stream()
                .map(p -> new ProfessorDTO(p.getId(), p.getNome(), p.getMatricula(), p.getEmail()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Professor salvar(@RequestBody Professor professor) {
        return professorService.salvar(professor);
    }

    @GetMapping("/{id}")
    public Professor buscarPorId(@PathVariable Long id) {
        return professorService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        professorService.deletar(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");
        Optional<Professor> professor = professorService.login(email, senha);
        Optional<Professor> professorOpt = professorService.buscarPorEmail(email);
        if (professor.isPresent()) {
            Professor professores = professorOpt.get();
            Map<Professor, List<Aula>> horarios = horarioService.getHorariosParaProfessoresCadastrados("C:\\Java Spring\\json\\schedule10.json");
            List<Aula> aulasProfessor = horarios.get(professores);
            return ResponseEntity.ok(aulasProfessor);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    @PostMapping("/save-work-days")
    public ResponseEntity<?> saveWorkDays() {
        Map<Professor, Set<String>> workDays = horarioService.getWorkDaysForProfessores("C:\\Java Spring\\json\\schedule10.json");
        horarioService.saveWorkDays(workDays);
        return ResponseEntity.ok("Work days saved successfully");
    }
    @GetMapping("/{id}/work-days")
    public ResponseEntity<?> getWorkDays(@PathVariable Long id) {
        Optional<Professor> professorOpt = Optional.ofNullable(professorService.buscarPorId(id));
        if (professorOpt.isPresent()) {
            Professor professor = professorOpt.get();
            Set<String> diasDaSemana = professor.getWorkDays().stream()
                    .map(DiaDaSemana::getName)
                    .collect(Collectors.toSet());

            ProfessorDiasAulasDTO dto = new ProfessorDiasAulasDTO(professor.getNome(), diasDaSemana);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/horarios")
    public ResponseEntity<?> getHorariosProfessor(@RequestParam String email) {
        Optional<Professor> professorOpt = professorService.buscarPorEmail(email);
        if (professorOpt.isPresent()) {
            Professor professor = professorOpt.get();
            Map<Professor, List<Aula>> horarios = horarioService.getHorariosParaProfessoresCadastrados("C:\\Java Spring\\json\\schedule10.json");
            List<Aula> aulasProfessor = horarios.get(professor);
            if (aulasProfessor != null) {
                return ResponseEntity.ok(aulasProfessor);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

