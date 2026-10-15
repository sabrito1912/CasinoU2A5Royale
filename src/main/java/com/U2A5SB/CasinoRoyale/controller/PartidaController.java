package com.U2A5SB.CasinoRoyale.controller;

import com.U2A5SB.CasinoRoyale.dto.IniciarPartidaRequest;
import com.U2A5SB.CasinoRoyale.dto.PartidaResponse;
import com.U2A5SB.CasinoRoyale.dto.TiroResponse;
import com.U2A5SB.CasinoRoyale.service.PartidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidas")
@RequiredArgsConstructor
public class PartidaController {

    private final PartidaService partidaService;

    @PostMapping
    public ResponseEntity<PartidaResponse> iniciarPartida(@Valid @RequestBody IniciarPartidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.iniciarPartida(request));
    }

    @PostMapping("/{id}/tiros")
    public ResponseEntity<TiroResponse> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.realizarTiro(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<PartidaResponse> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.finalizarPartidaManual(id));
    }
}