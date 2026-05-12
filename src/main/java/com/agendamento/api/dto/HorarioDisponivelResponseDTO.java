package com.agendamento.api.dto;

import java.time.LocalDateTime;

public class HorarioDisponivelResponseDTO {

    private Long id;
    private Long prestadorId;
    private String nomePrestador;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private boolean disponivel;

    public HorarioDisponivelResponseDTO() {}

    public HorarioDisponivelResponseDTO(Long id, Long prestadorId, String nomePrestador,
                                        LocalDateTime inicio, LocalDateTime fim, boolean disponivel) {
        this.id = id;
        this.prestadorId = prestadorId;
        this.nomePrestador = nomePrestador;
        this.inicio = inicio;
        this.fim = fim;
        this.disponivel = disponivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrestadorId() {
        return prestadorId;
    }

    public void setPrestadorId(Long prestadorId) {
        this.prestadorId = prestadorId;
    }

    public String getNomePrestador() {
        return nomePrestador;
    }

    public void setNomePrestador(String nomePrestador) {
        this.nomePrestador = nomePrestador;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}