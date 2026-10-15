package com.U2A5SB.CasinoRoyale.exception;

public class PartidaNoEnJuegoException extends RuntimeException {
    public PartidaNoEnJuegoException(Long id) {
        super("La partida con id " + id + " no está en juego");
    }
}