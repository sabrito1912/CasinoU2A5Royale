package com.U2A5SB.CasinoRoyale.exception;

public class JugadorNoEncontradoException extends RuntimeException {
    public JugadorNoEncontradoException(Long id) {
        super("No existe un jugador con id " + id);
    }
}