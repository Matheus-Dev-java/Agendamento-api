package com.agendamento.api.service;

import com.agendamento.api.dto.HorarioDisponivelRequestDTO;
import com.agendamento.api.dto.HorarioDisponivelResponseDTO;
import com.agendamento.api.exception.ConflitoDeHorarioException;
import com.agendamento.api.exception.RecursoNaoEncontradoException;
import com.agendamento.api.model.HorarioDisponivel;
import com.agendamento.api.model.Prestador;
import com.agendamento.api.repository.HorarioDisponivelRepository;
import com.agendamento.api.repository.PrestadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioDisponivelService {

    private final HorarioDisponivelRepository horarioRepository;
    private final PrestadorRepository prestadorRepository;

    public HorarioDisponivelService(HorarioDisponivelRepository horarioRepository,
                                    PrestadorRepository prestadorRepository) {
        this.horarioRepository = horarioRepository;
        this.prestadorRepository = prestadorRepository;
    }

    public HorarioDisponivelResponseDTO criar(HorarioDisponivelRequestDTO dto) {
        if (dto.getFim().isBefore(dto.getInicio()) || dto.getFim().isEqual(dto.getInicio())) {
            throw new IllegalArgumentException("O horario de fim deve ser posterior ao horario de inicio");
        }

        Prestador prestador = prestadorRepository.findById(dto.getPrestadorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prestador nao encontrado com o id: " + dto.getPrestadorId()));

        List<HorarioDisponivel> conflitos = horarioRepository.findConflitantes(
                dto.getPrestadorId(), dto.getInicio(), dto.getFim());

        if (!conflitos.isEmpty()) {
            throw new ConflitoDeHorarioException("O prestador ja possui um horario disponivel que conflita com o intervalo informado");
        }

        HorarioDisponivel horario = new HorarioDisponivel();
        horario.setPrestador(prestador);
        horario.setInicio(dto.getInicio());
        horario.setFim(dto.getFim());
        horario.setDisponivel(true);

        HorarioDisponivel salvo = horarioRepository.save(horario);
        return toResponseDTO(salvo);
    }

    public List<HorarioDisponivelResponseDTO> listarTodos() {
        return horarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<HorarioDisponivelResponseDTO> listarDisponiveisPorPrestador(Long prestadorId) {
        if (!prestadorRepository.existsById(prestadorId)) {
            throw new RecursoNaoEncontradoException("Prestador nao encontrado com o id: " + prestadorId);
        }
        return horarioRepository.findByPrestadorIdAndDisponivelTrue(prestadorId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public HorarioDisponivelResponseDTO buscarPorId(Long id) {
        HorarioDisponivel horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Horario nao encontrado com o id: " + id));
        return toResponseDTO(horario);
    }

    public void deletar(Long id) {
        if (!horarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Horario nao encontrado com o id: " + id);
        }
        horarioRepository.deleteById(id);
    }

    private HorarioDisponivelResponseDTO toResponseDTO(HorarioDisponivel horario) {
        return new HorarioDisponivelResponseDTO(
                horario.getId(),
                horario.getPrestador().getId(),
                horario.getPrestador().getNome(),
                horario.getInicio(),
                horario.getFim(),
                horario.isDisponivel()
        );
    }
}