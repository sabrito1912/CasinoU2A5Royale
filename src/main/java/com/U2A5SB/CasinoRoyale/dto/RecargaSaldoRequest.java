package com.U2A5SB.CasinoRoyale.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecargaSaldoRequest {

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto a recargar debe ser mayor a 0")
    private Double monto;
}