package com.agendamento.api.controller;

import com.agendamento.api.dto.PrestadorRequestDTO;
import com.agendamento.api.dto.PrestadorResponseDTO;
import com.agendamento.api.service.PrestadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestadores")
public class PrestadorController {

    private final PrestadorService prestadorService;

    public PrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    @PostMapping
    public ResponseEntity<PrestadorResponseDTO> criar(@Valid @RequestBody PrestadorRequestDTO dto) {
        PrestadorResponseDTO criado = prestadorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<PrestadorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(prestadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestadorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prestadorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestadorResponseDTO> atualizar(@PathVariable Long id,
                                                          @Valid @RequestBody PrestadorRequestDTO dto) {
        return ResponseEntity.ok(prestadorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        prestadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}