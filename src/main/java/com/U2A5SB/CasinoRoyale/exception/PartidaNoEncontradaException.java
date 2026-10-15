package com.U2A5SB.CasinoRoyale.exception;

public class PartidaNoEncontradaException extends RuntimeException {
    public PartidaNoEncontradaException(Long id) {
        super("No existe una partida con id " + id);
    }
}