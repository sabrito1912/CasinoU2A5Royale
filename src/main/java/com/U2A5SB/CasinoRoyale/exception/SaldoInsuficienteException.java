package com.U2A5SB.CasinoRoyale.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(Double saldo, Double apuesta) {
        super("Saldo insuficiente. Saldo actual: " + saldo + ", apuesta: " + apuesta);
    }
}