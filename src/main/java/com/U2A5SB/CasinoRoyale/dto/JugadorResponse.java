package com.U2A5SB.CasinoRoyale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JugadorResponse {
    private Long id;
    private String nombre;
    private Double saldo;
    private Boolean activo;
}