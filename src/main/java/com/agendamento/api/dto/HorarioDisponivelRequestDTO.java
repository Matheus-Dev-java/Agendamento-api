package com.agendamento.api.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class HorarioDisponivelRequestDTO {

    @NotNull(message = "O ID do prestador e obrigatorio")
    private Long prestadorId;

    @NotNull(message = "O horario de inicio e obrigatorio")
    private LocalDateTime inicio;

    @NotNull(message = "O horario de fim e obrigatorio")
    private LocalDateTime fim;

    public Long getPrestadorId() {
        return prestadorId;
    }

    public void setPrestadorId(Long prestadorId) {
        this.prestadorId = prestadorId;
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
}