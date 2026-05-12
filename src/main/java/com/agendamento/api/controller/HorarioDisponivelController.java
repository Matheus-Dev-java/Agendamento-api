package com.agendamento.api.controller;

import com.agendamento.api.dto.HorarioDisponivelRequestDTO;
import com.agendamento.api.dto.HorarioDisponivelResponseDTO;
import com.agendamento.api.service.HorarioDisponivelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioDisponivelController {

    private final HorarioDisponivelService horarioService;

    public HorarioDisponivelController(HorarioDisponivelService horarioService) {
        this.horarioService = horarioService;
    }

    @PostMapping
    public ResponseEntity<HorarioDisponivelResponseDTO> criar(@Valid @RequestBody HorarioDisponivelRequestDTO dto) {
        HorarioDisponivelResponseDTO criado = horarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<HorarioDisponivelResponseDTO>> listarTodos() {
        return ResponseEntity.ok(horarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDisponivelResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(horarioService.buscarPorId(id));
    }

    @GetMapping("/prestador/{prestadorId}/disponiveis")
    public ResponseEntity<List<HorarioDisponivelResponseDTO>> listarDisponiveisPorPrestador(
            @PathVariable Long prestadorId) {
        return ResponseEntity.ok(horarioService.listarDisponiveisPorPrestador(prestadorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        horarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}