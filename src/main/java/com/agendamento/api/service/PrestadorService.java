package com.agendamento.api.service;

import com.agendamento.api.dto.PrestadorRequestDTO;
import com.agendamento.api.dto.PrestadorResponseDTO;
import com.agendamento.api.exception.RecursoNaoEncontradoException;
import com.agendamento.api.model.Prestador;
import com.agendamento.api.repository.PrestadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;

    public PrestadorService(PrestadorRepository prestadorRepository) {
        this.prestadorRepository = prestadorRepository;
    }

    public PrestadorResponseDTO criar(PrestadorRequestDTO dto) {
        if (prestadorRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ja existe um prestador cadastrado com o email: " + dto.getEmail());
        }

        Prestador prestador = new Prestador();
        prestador.setNome(dto.getNome());
        prestador.setEmail(dto.getEmail());
        prestador.setEspecialidade(dto.getEspecialidade());
        prestador.setTelefone(dto.getTelefone());

        Prestador salvo = prestadorRepository.save(prestador);
        return toResponseDTO(salvo);
    }

    public List<PrestadorResponseDTO> listarTodos() {
        return prestadorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PrestadorResponseDTO buscarPorId(Long id) {
        Prestador prestador = prestadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prestador nao encontrado com o id: " + id));
        return toResponseDTO(prestador);
    }

    public PrestadorResponseDTO atualizar(Long id, PrestadorRequestDTO dto) {
        Prestador prestador = prestadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prestador nao encontrado com o id: " + id));

        boolean emailEmUso = prestadorRepository.findByEmail(dto.getEmail())
                .map(p -> !p.getId().equals(id))
                .orElse(false);

        if (emailEmUso) {
            throw new IllegalArgumentException("O email " + dto.getEmail() + " ja esta em uso por outro prestador");
        }

        prestador.setNome(dto.getNome());
        prestador.setEmail(dto.getEmail());
        prestador.setEspecialidade(dto.getEspecialidade());
        prestador.setTelefone(dto.getTelefone());

        Prestador atualizado = prestadorRepository.save(prestador);
        return toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!prestadorRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Prestador nao encontrado com o id: " + id);
        }
        prestadorRepository.deleteById(id);
    }

    private PrestadorResponseDTO toResponseDTO(Prestador prestador) {
        return new PrestadorResponseDTO(
                prestador.getId(),
                prestador.getNome(),
                prestador.getEmail(),
                prestador.getEspecialidade(),
                prestador.getTelefone()
        );
    }
}