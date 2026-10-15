package com.U2A5SB.CasinoRoyale.service;

import com.U2A5SB.CasinoRoyale.dto.JugadorRequest;
import com.U2A5SB.CasinoRoyale.dto.JugadorResponse;
import com.U2A5SB.CasinoRoyale.dto.RecargaSaldoRequest;
import com.U2A5SB.CasinoRoyale.exception.JugadorNoEncontradoException;
import com.U2A5SB.CasinoRoyale.model.Jugador;
import com.U2A5SB.CasinoRoyale.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    // Auxiliar para poder tener jugadores de prueba
    @Transactional
    public JugadorResponse crearJugador(JugadorRequest request) {
        Jugador jugador = new Jugador();
        jugador.setNombre(request.getNombre());
        jugador.setSaldo(request.getSaldo());
        jugador.setActivo(true);
        return aRespuesta(jugadorRepository.save(jugador));
    }

    @Transactional
    public JugadorResponse recargarSaldo(Long jugadorId, RecargaSaldoRequest request) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new JugadorNoEncontradoException(jugadorId));

        jugador.setSaldo(jugador.getSaldo() + request.getMonto());
        return aRespuesta(jugadorRepository.save(jugador));
    }

    private JugadorResponse aRespuesta(Jugador j) {
        return new JugadorResponse(j.getId(), j.getNombre(), j.getSaldo(), j.getActivo());
    }
}