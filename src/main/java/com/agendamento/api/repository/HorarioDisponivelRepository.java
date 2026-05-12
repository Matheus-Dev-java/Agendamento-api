package com.agendamento.api.repository;

import com.agendamento.api.model.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {

    List<HorarioDisponivel> findByPrestadorIdAndDisponivelTrue(Long prestadorId);

    @Query("SELECT h FROM HorarioDisponivel h WHERE h.prestador.id = :prestadorId " +
            "AND h.disponivel = true " +
            "AND ((h.inicio < :fim) AND (h.fim > :inicio))")
    List<HorarioDisponivel> findConflitantes(@Param("prestadorId") Long prestadorId,
                                             @Param("inicio") LocalDateTime inicio,
                                             @Param("fim") LocalDateTime fim);
}