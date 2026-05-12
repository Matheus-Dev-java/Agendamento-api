package com.agendamento.api.dto;

import com.agendamento.api.model.Reserva;
import java.time.LocalDateTime;

public class ReservaResponseDTO {

    private Long id;
    private String nomeCliente;
    private String emailCliente;
    private String telefoneCliente;
    private Long horarioId;
    private LocalDateTime inicioHorario;
    private LocalDateTime fimHorario;
    private String nomePrestador;
    private LocalDateTime criadoEm;
    private Reserva.StatusReserva status;

    public ReservaResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public Long getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Long horarioId) {
        this.horarioId = horarioId;
    }

    public LocalDateTime getInicioHorario() {
        return inicioHorario;
    }

    public void setInicioHorario(LocalDateTime inicioHorario) {
        this.inicioHorario = inicioHorario;
    }

    public LocalDateTime getFimHorario() {
        return fimHorario;
    }

    public void setFimHorario(LocalDateTime fimHorario) {
        this.fimHorario = fimHorario;
    }

    public String getNomePrestador() {
        return nomePrestador;
    }

    public void setNomePrestador(String nomePrestador) {
        this.nomePrestador = nomePrestador;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Reserva.StatusReserva getStatus() {
        return status;
    }

    public void setStatus(Reserva.StatusReserva status) {
        this.status = status;
    }
}