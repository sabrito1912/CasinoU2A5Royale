package com.U2A5SB.CasinoRoyale.exception;

public class JugadorInactivoException extends RuntimeException {
    public JugadorInactivoException(Long id) {
        super("El jugador con id " + id + " no está activo");
    }
}