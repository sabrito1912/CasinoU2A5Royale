package com.U2A5SB.CasinoRoyale.service;

import com.U2A5SB.CasinoRoyale.dto.IniciarPartidaRequest;
import com.U2A5SB.CasinoRoyale.dto.PartidaResponse;
import com.U2A5SB.CasinoRoyale.dto.TiroResponse;
import com.U2A5SB.CasinoRoyale.exception.*;
import com.U2A5SB.CasinoRoyale.model.EstadoPartida;
import com.U2A5SB.CasinoRoyale.model.Jugador;
import com.U2A5SB.CasinoRoyale.model.Partida;
import com.U2A5SB.CasinoRoyale.model.Tiro;
import com.U2A5SB.CasinoRoyale.repository.JugadorRepository;
import com.U2A5SB.CasinoRoyale.repository.PartidaRepository;
import com.U2A5SB.CasinoRoyale.repository.TiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final TiroRepository tiroRepository;

    private final Random random = new Random();

    //  iniciarPartida
    @Transactional
    public PartidaResponse iniciarPartida(IniciarPartidaRequest request) {
        Jugador jugador = jugadorRepository.findById(request.getJugadorId())
                .orElseThrow(() -> new JugadorNoEncontradoException(request.getJugadorId()));

        if (!jugador.getActivo()) {
            throw new JugadorInactivoException(jugador.getId());
        }

        if (jugador.getSaldo() < request.getApuesta()) {
            throw new SaldoInsuficienteException(jugador.getSaldo(), request.getApuesta());
        }

        // Descontar la apuesta del saldo
        jugador.setSaldo(jugador.getSaldo() - request.getApuesta());
        jugadorRepository.save(jugador);

        // Crear la partida directamente en EN_JUEGO
        Partida partida = new Partida();
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(request.getApuesta());

        return aPartidaResponse(partidaRepository.save(partida));
    }

    //  realizarTiro
    @Transactional
    public TiroResponse realizarTiro(Long partidaId) {
        Partida partida = buscarPartida(partidaId);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new PartidaNoEnJuegoException(partidaId);
        }

        // Generar 2 dados y calcular la suma
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        Jugador jugador = partida.getJugador();
        String resultado;
        boolean esGanador = false;

        if (suma == 7 || suma == 11) {
            resultado = "GANA";
            esGanador = true;
            partida.setEstado(EstadoPartida.FINALIZADA);
            jugador.setSaldo(jugador.getSaldo() + partida.getApuesta() * 2);
            jugadorRepository.save(jugador);
            partidaRepository.save(partida);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            resultado = "PIERDE";
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
        } else {
            resultado = "CONTINUA"; // la partida sigue EN_JUEGO
        }

        // Guardar el tiro como último paso
        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(esGanador);
        tiro = tiroRepository.save(tiro);

        return new TiroResponse(tiro.getId(), partida.getId(), dado1, dado2, suma,
                esGanador, resultado, jugador.getSaldo());
    }

    //  finalizarPartidaManual
    @Transactional
    public PartidaResponse finalizarPartidaManual(Long partidaId) {
        Partida partida = buscarPartida(partidaId);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new PartidaNoEnJuegoException(partidaId);
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return aPartidaResponse(partidaRepository.save(partida));
    }

    //  obtenerHistorialPartida
    @Transactional(readOnly = true)
    public List<PartidaResponse> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new JugadorNoEncontradoException(jugadorId);
        }

        return partidaRepository.findByJugadorIdOrderByFechaDesc(jugadorId)
                .stream()
                .map(this::aPartidaResponse)
                .toList();
    }

    //  auxiliares
    private Partida buscarPartida(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNoEncontradaException(id));
    }

    private PartidaResponse aPartidaResponse(Partida p) {
        return new PartidaResponse(p.getId(), p.getFecha(), p.getEstado(),
                p.getJugador().getId(), p.getApuesta());
    }
}