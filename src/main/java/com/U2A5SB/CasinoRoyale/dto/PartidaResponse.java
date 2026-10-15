package com.U2A5SB.CasinoRoyale.dto;

import com.U2A5SB.CasinoRoyale.model.EstadoPartida;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PartidaResponse {
    private Long id;
    private LocalDateTime fecha;
    private EstadoPartida estado;
    private Long jugadorId;
    private Double apuesta;
}