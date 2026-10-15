package com.U2A5SB.CasinoRoyale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TiroResponse {
    private Long id;
    private Long partidaId;
    private Integer valorDado1;
    private Integer valorDado2;
    private Integer suma;
    private Boolean esGanador;
    private String resultado;       // "GANA", "PIERDE" o "CONTINUA"
    private Double saldoActual;     // saldo del jugador después del tiro
}