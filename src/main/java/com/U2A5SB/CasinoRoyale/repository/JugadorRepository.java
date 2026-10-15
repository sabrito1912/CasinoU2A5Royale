package com.U2A5SB.CasinoRoyale.repository;

import com.U2A5SB.CasinoRoyale.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}