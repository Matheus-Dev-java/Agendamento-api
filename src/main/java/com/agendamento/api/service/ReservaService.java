package com.agendamento.api.service;

import com.agendamento.api.dto.ReservaRequestDTO;
import com.agendamento.api.dto.ReservaResponseDTO;
import com.agendamento.api.exception.ConflitoDeHorarioException;
import com.agendamento.api.exception.RecursoNaoEncontradoException;
import com.agendamento.api.model.HorarioDisponivel;
import com.agendamento.api.model.Reserva;
import com.agendamento.api.repository.HorarioDisponivelRepository;
import com.agendamento.api.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HorarioDisponivelRepository horarioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          HorarioDisponivelRepository horarioRepository) {
        this.reservaRepository = reservaRepository;
        this.horarioRepository = horarioRepository;
    }

    @Transactional
    public ReservaResponseDTO criar(ReservaRequestDTO dto) {
        HorarioDisponivel horario = horarioRepository.findById(dto.getHorarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Horario nao encontrado com o id: " + dto.getHorarioId()));

        if (!horario.isDisponivel()) {
            throw new ConflitoDeHorarioException("O horario selecionado ja esta reservado");
        }

        horario.setDisponivel(false);
        horarioRepository.save(horario);

        Reserva reserva = new Reserva();
        reserva.setNomeCliente(dto.getNomeCliente());
        reserva.setEmailCliente(dto.getEmailCliente());
        reserva.setTelefoneCliente(dto.getTelefoneCliente());
        reserva.setHorario(horario);
        reserva.setCriadoEm(LocalDateTime.now());
        reserva.setStatus(Reserva.StatusReserva.CONFIRMADA);

        Reserva salva = reservaRepository.save(reserva);
        return toResponseDTO(salva);
    }

    public List<ReservaResponseDTO> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva nao encontrada com o id: " + id));
        return toResponseDTO(reserva);
    }

    public List<ReservaResponseDTO> buscarPorEmailCliente(String emailCliente) {
        return reservaRepository.findByEmailCliente(emailCliente)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservaResponseDTO cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva nao encontrada com o id: " + id));

        if (reserva.getStatus() == Reserva.StatusReserva.CANCELADA) {
            throw new IllegalArgumentException("A reserva ja esta cancelada");
        }

        reserva.setStatus(Reserva.StatusReserva.CANCELADA);

        HorarioDisponivel horario = reserva.getHorario();
        horario.setDisponivel(true);
        horarioRepository.save(horario);

        Reserva atualizada = reservaRepository.save(reserva);
        return toResponseDTO(atualizada);
    }

    private ReservaResponseDTO toResponseDTO(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(reserva.getId());
        dto.setNomeCliente(reserva.getNomeCliente());
        dto.setEmailCliente(reserva.getEmailCliente());
        dto.setTelefoneCliente(reserva.getTelefoneCliente());
        dto.setHorarioId(reserva.getHorario().getId());
        dto.setInicioHorario(reserva.getHorario().getInicio());
        dto.setFimHorario(reserva.getHorario().getFim());
        dto.setNomePrestador(reserva.getHorario().getPrestador().getNome());
        dto.setCriadoEm(reserva.getCriadoEm());
        dto.setStatus(reserva.getStatus());
        return dto;
    }
}