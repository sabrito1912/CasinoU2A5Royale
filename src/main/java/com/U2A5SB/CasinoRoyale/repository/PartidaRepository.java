package com.U2A5SB.CasinoRoyale.repository;

import com.U2A5SB.CasinoRoyale.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    // Historial: todas las partidas de un jugador, de la más reciente a la más antigua
    List<Partida> findByJugadorIdOrderByFechaDesc(Long jugadorId);
}