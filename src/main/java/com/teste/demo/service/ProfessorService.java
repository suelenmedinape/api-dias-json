package com.teste.demo.service;

import com.teste.demo.model.DiaDaSemana;
import com.teste.demo.model.Professor;
import com.teste.demo.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    public List<Professor> listarTodos() {
        return repository.findAll();
    }

    public Professor salvar(Professor professor) {
        return repository.save(professor);
    }

    public Professor buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Optional<Professor> login(String email, String senha) {
        Optional<Professor> professorOpt = repository.findByEmail(email);
        if (professorOpt.isPresent() && professorOpt.get().getSenha().equals(senha)) {
            return professorOpt;
        }
        return Optional.empty();
    }

    public Optional<Professor> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }
}
