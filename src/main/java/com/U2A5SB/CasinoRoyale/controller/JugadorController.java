package com.U2A5SB.CasinoRoyale.controller;

import com.U2A5SB.CasinoRoyale.dto.JugadorRequest;
import com.U2A5SB.CasinoRoyale.dto.JugadorResponse;
import com.U2A5SB.CasinoRoyale.dto.PartidaResponse;
import com.U2A5SB.CasinoRoyale.dto.RecargaSaldoRequest;
import com.U2A5SB.CasinoRoyale.service.JugadorService;
import com.U2A5SB.CasinoRoyale.service.PartidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class JugadorController {

    private final JugadorService jugadorService;
    private final PartidaService partidaService;

    @PostMapping
    public ResponseEntity<JugadorResponse> crearJugador(@Valid @RequestBody JugadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.crearJugador(request));
    }

    @PutMapping("/{id}/recarga")
    public ResponseEntity<JugadorResponse> recargarSaldo(@PathVariable Long id,
                                                         @Valid @RequestBody RecargaSaldoRequest request) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(id, request));
    }

    @GetMapping("/{id}/partidas")
    public ResponseEntity<List<PartidaResponse>> obtenerHistorialPartida(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.obtenerHistorialPartida(id));
    }
}